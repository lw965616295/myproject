package com.weil.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName NioClient2
 * @Author weil
 * @Description //客户端
 * @Date 2021/7/22 10:23
 * @Version 1.0.0
 **/
public class NioClient2 {
    public static void main(String[] args) {
//        try (SocketChannel sc = SocketChannel.open()) {
//            sc.connect(new InetSocketAddress("127.0.0.1",8081));
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try (Socket socket = new Socket("localhost", 8081)) {
            System.out.println(socket);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello\\n".getBytes(StandardCharsets.UTF_8));
            outputStream.write("world\\n".getBytes(StandardCharsets.UTF_8));
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
