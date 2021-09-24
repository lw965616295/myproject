package com.weil.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName NettyAdvance
 * @Author weil
 * @Description //netty参数优化
 * @Date 2021/9/24 15:10
 * @Version 1.0.0
 **/
@Slf4j
public class NettyAdvance {
    public static void main(String[] args) {
        NioEventLoopGroup loopGroup = null;
        try {
            loopGroup = new NioEventLoopGroup();
            ChannelFuture future = new Bootstrap()
                    .group(loopGroup)
                    // 客户端连接超时抛异常
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 300)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler());
                            ch.pipeline().addLast(new StringEncoder());
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("连接成功，{}", ctx.channel().remoteAddress());
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

                                }
                            });
                        }
                    }).connect("127.0.0.1", 8080).sync();
        } catch (Exception e) {
            log.error("客户端发生异常", e);
            loopGroup.shutdownGracefully();
        }
    }
}
