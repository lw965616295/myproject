package com.weil.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @ClassName NettyClient1
 * @Author weil
 * @Description //连接建立方式
 * @Date 2021/8/30 16:52
 * @Version 1.0.0
 **/
@Slf4j
public class NettyClient1 {
    public static void main(String[] args) {
        String str = "粘包\n" +
                "\n" +
                "* 现象，发送 abc def，接收 abcdef\n" +
                "* 原因\n" +
                "  * 应用层：接收方 ByteBuf 设置太大（Netty 默认 1024）\n" +
                "  * 滑动窗口：假设发送方 256 bytes 表示一个完整报文，但由于接收方处理不及时且窗口大小足够大，这 256 bytes 字节就会缓冲在接收方的滑动窗口中，当滑动窗口中缓冲了多个报文就会粘包\n" +
                "  * Nagle 算法：会造成粘包";
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            ChannelFuture future = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new StringEncoder());
                            channel.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    log.debug("建立连接！");
                                    ctx.channel().writeAndFlush(str);
                                    ctx.close();
                                }

                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
                                    log.debug("接受到的消息：{}", s);
                                }
                            });
                        }
                    }).connect(new InetSocketAddress(8081)).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            group.shutdownGracefully();
        }finally {
//            group.shutdownGracefully();
        }
    }
}
