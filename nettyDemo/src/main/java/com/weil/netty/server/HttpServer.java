package com.weil.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

/**
 * @ClassName HttpServer
 * @Author weil
 * @Description //模仿http服务端
 * @Date 2021/9/3 16:21
 * @Version 1.0.0
 **/
@Slf4j
public class HttpServer {
    public static void main(String[] args) {
        // 专门处理连接
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 专门处理各种事件
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ChannelFuture future = new ServerBootstrap()
                    // 事件处理线程池一个
                    .group(boss, worker)
                    // 指定channel类型：服务端
                    .channel(NioServerSocketChannel.class)
                    // 指定处理器，处理事件
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 绑定对应的处理器 处理流水线 按顺序
                            ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));
                            // 解码器使用支持http协议
                            ch.pipeline().addLast(new HttpServerCodec());
                            // 入站处理，此时处理的是read事件（netty已经做了accept事件）
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("建立连接！");
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest httpRequest) throws Exception {
                                    log.debug("uri:{}", httpRequest.uri());
                                    // 返回
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpRequest.protocolVersion(), HttpResponseStatus.OK);
                                    byte[] bytes = "<h1>hello world!</h1>".getBytes(StandardCharsets.UTF_8);
                                    // 设置响应体
                                    response.content().writeBytes(bytes);
                                    // 设置头信息，记录内容长度，告诉浏览器长度，不然会一直等待数据
                                    response.headers().setInt(CONTENT_LENGTH, bytes.length);
                                    ctx.writeAndFlush(response);
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
