package com.weil.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName FileOper
 * @Author weil
 * @Description //文件操作
 * @Date 2021/7/19 17:06
 * @Version 1.0.0
 **/
public class FileOper {
    /**
     * 拷贝文件
     */
    @Test
    public void copyFile() throws Exception {
        String inPath = "D:\\BaiduNetdiskDownload\\Netty教程源码资料\\讲义\\Netty01-nio.md";
        String outPath = "D:\\BaiduNetdiskDownload\\Netty教程源码资料\\讲义\\ab.md";
        long start = System.nanoTime();
        try (FileChannel in = new FileInputStream(inPath).getChannel();
             FileChannel out =  new FileOutputStream(outPath).getChannel()) {
            in.transferTo(0, in.size(), out);
        }catch (Exception e){
            e.printStackTrace();
        }
        long end = System.nanoTime();
        System.out.println("用时："+(end-start)/1000_000_000+"秒");
    }

    /**
     * 拷贝文件，大于2g
     */
    @Test
    public void copyFile2() {
        String inPath = "E:\\linux环境\\CentOS-7-x86_64-DVD-2009.iso";
        String outPath = "E:\\linux环境\\abc.iso";
        long start = System.nanoTime();
        try (FileChannel in = new FileInputStream(inPath).getChannel();
             FileChannel out =  new FileOutputStream(outPath).getChannel()) {
            long size = in.size();
            long left = in.size();
            while (left>0){
                System.out.println("pos:"+(size-left)+",left:"+left);
                left -= in.transferTo(size-left, left, out);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        long end = System.nanoTime();
        System.out.println("用时："+(end-start)/1000_000_000+"秒");
        //pos:0,left:4712300544
        //pos:2147483647,left:2564816897
        //pos:4294967294,left:417333250
        //用时：58秒
    }

    /**
     * path 操作
     */
    @Test
    public void pathTest(){
        Path path = Paths.get("E:\\linux环境\\abc.iso");
        System.out.println(path);
        System.out.println(path.isAbsolute());
        System.out.println(Files.exists(path));
    }
    /**
     * files 操作
     */
    @Test
    public void filesTest() throws IOException {
        Path source = Paths.get("E:\\linux环境\\CentOS-7-x86_64-DVD-2009.iso");
        Path dest = Paths.get("E:\\linux环境\\abc.iso");
        Files.copy(source, dest);
//        Files.move(source, dest);
    }
}
