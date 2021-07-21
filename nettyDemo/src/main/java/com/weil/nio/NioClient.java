package com.weil.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @ClassName NioClient
 * @Author weil
 * @Description //nio clients
 * @Date 2021/7/19 13:28
 * @Version 1.0.0
 **/
public class NioClient {
    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.connect(new InetSocketAddress("127.0.0.1",8081));
            System.out.println("等待！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
