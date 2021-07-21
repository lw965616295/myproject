package com.weil.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName NioServer
 * @Author weil
 * @Description //nio server
 * @Date 2021/7/19 13:27
 * @Version 1.0.0
 **/
@Slf4j
public class NioServer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1.创建服务端channel
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            // 2.绑定端口
            ssc.bind(new InetSocketAddress(8081));
            // 非阻塞
            ssc.configureBlocking(false);
            // 通道channel集合
            List<SocketChannel> channels = new ArrayList<>();
            while (true) {
                log.debug("connecting...");
                // 3.接受客户端来的连接(此方法阻塞线程),设置非阻塞时，没有连接返回null
                SocketChannel channel = ssc.accept();
                if(channel != null){
                    channel.configureBlocking(false);
                    // 通过上一步代表连接上了客户端
                    log.debug("connected...{}", channel);
                    channels.add(channel);
                }

                // 4.遍历处理对应channel
                for (SocketChannel socketChannel : channels) {
                    // 5.接受客户端发来的信息(该方法阻塞)，设置非阻塞后，如果读不到信息则返回0
                    int read = socketChannel.read(buffer);
                    if(read > 0){
                        log.debug("读到信息...");
                        buffer.flip();
                        log.debug(Charset.defaultCharset().decode(buffer).toString());
                        buffer.clear();
                    }

                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
