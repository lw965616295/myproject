package com.weil.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @ClassName NettyServer
 * @Author weil
 * @Description //netty服务器
 * @Date 2021/6/30 10:18
 * @Version 1.0.0
 **/
@Slf4j
public class NettyServer {
    public static void main(String[] args) {
        // 专门处理连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 专门处理各种事件
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            new ServerBootstrap()
                    // 事件处理线程池一个
                    .group(boss, worker)
                    // 指定channel类型：服务端
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_RCVBUF, 5) 用于测试半包现象，调小buf
                    // 指定处理器，处理事件
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            // 绑定对应的处理器 处理流水线 按顺序
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            // 解码器
                            ch.pipeline().addLast(new StringDecoder());
                            // 入站处理，此时处理的是read事件（netty已经做了accept事件）
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("连接建立,{}", ctx.channel().remoteAddress());
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
                                    log.debug("收到{}发来的信息：{}", ctx.channel().remoteAddress(), s);
                                }
                            });
                        }
                    }).bind(new InetSocketAddress(8081));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
