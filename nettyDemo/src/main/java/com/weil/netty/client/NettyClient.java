package com.weil.netty.client;

import com.weil.netty.server.KryoDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName NettyClient
 * @Author weil
 * @Description //客户端
 * @Date 2021/6/30 14:02
 * @Version 1.0.0
 **/
public class NettyClient {
    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new KryoEncoder());
                            socketChannel.pipeline().addLast(new KryoDecoder());
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });
            System.out.println("client start !!!");
            ChannelFuture future = bootstrap.connect("localhost", 8888);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
