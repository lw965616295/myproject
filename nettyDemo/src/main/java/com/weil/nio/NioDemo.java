package com.weil.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * @ClassName NioDemo
 * @Author weil
 * @Description //TODO
 * @Date 2021/7/2 16:59
 * @Version 1.0.0
 **/
public class NioDemo {
    public static void main(String[] args) {
        // 类加载器获取文件路径
        String path = NioDemo.class.getClassLoader().getResource("demo.txt").getPath();
        try (FileChannel channel = new FileInputStream(path).getChannel()) {

            System.out.println("hahha");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
