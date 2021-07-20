package com.weil.nio;

import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName NioClient
 * @Author weil
 * @Description //nio clients
 * @Date 2021/7/19 13:28
 * @Version 1.0.0
 **/
public class NioClient {
    public static void main(String[] args) {
        ServerSocketChannel ssc = new NioServerSocketChannel();
    }
}
