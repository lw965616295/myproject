package com.weil.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName NioServer2
 * @Author weil
 * @Description //单线程nio多路复用方式(selector使用)
 * @Date 2021/7/21 17:13
 * @Version 1.0.0
 **/
@Slf4j
public class NioServer2 {
    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(8081));
            // 1.开启非阻塞模式
            ssc.configureBlocking(false);
            // 2.selector
            Selector selector = Selector.open();
            // 3.绑定连接事件到selector，前提是channel必须开启非阻塞，否则会抛异常java.nio.channels.IllegalBlockingModeException
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                // 4.该方法为阻塞，有事件发生才执行
                int count = selector.select();
                log.debug("select count:{}", count);
                // 5.遍历selectKeys
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectedKey = iterator.next();
                    if(selectedKey.isAcceptable()){

                        ServerSocketChannel channel = (ServerSocketChannel) selectedKey.channel();
                        // 事件必须处理，否则selector.select()就一直存在这个事件，不再阻塞
                        SocketChannel sc = channel.accept();
                        // 连接成功事件
                        log.debug("connected...{}", sc);
                        // 绑定读事件
                        sc.configureBlocking(false);
                        sc.register(selector, SelectionKey.OP_READ);
                    }else if(selectedKey.isReadable()){
                        SocketChannel channel = (SocketChannel) selectedKey.channel();
                        ByteBuffer bb = ByteBuffer.allocate(4);
                        int read = 0;
                        try {
                            read = channel.read(bb);
                        } catch (IOException e) {
                            e.printStackTrace();
                            read = -1;
                        }
                        // 没有读出信息等，出现的异常
                        if(read == -1){
                            // 对于事件要么处理要么cancel
                            selectedKey.cancel();
                            channel.close();
                        }else {
                            bb.flip();
//                            log.debug("读出：{}", Charset.defaultCharset().decode(bb).toString());
                            NioDemo.packetMethod(bb);
                            bb.clear();
                        }
                    }
                    // 移除key，防止再次进入
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
