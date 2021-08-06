package com.weil.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @ClassName NioClient
 * @Author weil
 * @Description //nio clients 读取客户端信息
 * @Date 2021/8/06 09:48
 * @Version 1.0.0
 **/
@Slf4j
public class NioClient3 {
    public static void main(String[] args) {
        try (SocketChannel sc = SocketChannel.open();Selector selector = Selector.open()) {
            // 关闭阻塞
            sc.configureBlocking(false);
            // 注册连接和读事件
            sc.register(selector, SelectionKey.OP_CONNECT|SelectionKey.OP_READ);
            sc.connect(new InetSocketAddress("127.0.0.1",8081));
            while (true){
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()){
                    SelectionKey sk = iter.next();
                    iter.remove();
                    if (sk.isConnectable()) {
                        log.debug("已连接：{}",sc.getRemoteAddress().toString());
                        // 处理事件
                        if (sc.finishConnect()) {
                            sc.write(Charset.defaultCharset().encode("hahha"));
                        }
                    }else if (sk.isReadable()){
                        ByteBuffer bb = ByteBuffer.allocate(10);
                        sc.read(bb);
                        bb.flip();
                        log.debug("读出：{}", Charset.defaultCharset().decode(bb).toString());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
