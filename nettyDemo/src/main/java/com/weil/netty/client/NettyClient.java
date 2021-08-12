package com.weil.netty.client;

import com.weil.netty.common.KryoEncoder;
import com.weil.netty.common.KryoDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @ClassName NettyClient
 * @Author weil
 * @Description //客户端
 * @Date 2021/6/30 14:02
 * @Version 1.0.0
 **/
@Slf4j
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        // 模板代码和server端相识
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // 编码器
                        ch.pipeline().addLast(new StringEncoder());
                    }
                }).connect(new InetSocketAddress(8081))
                // connect是异步的，所以需要等待连接好在继续下面操作
                .sync()
                .channel();
        channel.writeAndFlush("hello world!");
        // 开启线程获取控制台输入的信息
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (true){
                log.debug("请输入：");
                // 没有信息就阻塞
                String next = scanner.next();
                channel.writeAndFlush(next);
            }
        },"子线程").start();
    }
}
