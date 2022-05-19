package com.weil.de;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @ClassName AccessFile
 * @Author weil
 * @Description //访问文件
 * @Date 2022/3/17 16:43
 * @Version 1.0.0
 **/
public class AccessFile {
    // 使用RandomAccessFile操作文件
    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\Users\\A\\Desktop\\data.log");
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\A\\Desktop\\data.log", "r");
        long filePointer = raf.getFilePointer();
        System.out.println("读取开始！");
        System.out.println("开始点:" + filePointer);
        long start = System.currentTimeMillis();
        Integer count = 1000;
        String line = null;
        while ((line = raf.readLine()) != null){
            if(count == 0){
                break;
            }
            System.out.print(count+":");
            System.out.println(line);
            count--;
        }
        long end = System.currentTimeMillis();
        System.out.println("读取1000条记录耗时："+(end-start));
    }
}
