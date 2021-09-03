package com.weil.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @ClassName NettyClient1
 * @Author weil
 * @Description //连接建立方式
 * @Date 2021/8/30 16:52
 * @Version 1.0.0
 **/
public class NettyClient1 {
    public static void main(String[] args) {
        ChannelFuture future = new Bootstrap().group(new NioEventLoopGroup(2))
                .channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<String>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

                    }
                }).connect(new InetSocketAddress(8081));
//        future.sync() 与下面的作用一样
        future.addListener((ChannelFutureListener) channelFuture -> {
            System.out.println("建立连接");
            channelFuture.channel().writeAndFlush("haha");
        });
    }
}
