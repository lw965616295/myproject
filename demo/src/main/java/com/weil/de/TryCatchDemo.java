/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.de;

import java.util.Scanner;

/**
 * @ClassName TryCatchDemo
 * @Author liwei
 * @Description //TODO
 * @Date 15:52 2021/2/25
 * @Version 1.0.0
 **/
public class TryCatchDemo {
    public static void main(String[] args) {
        //实现了Closeable或者AutoCloseable接口的类可以try-with-resources进行改造
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
            String next = scanner.next();
            System.out.println(next);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(scanner != null){
                scanner.close();
            }
        }

        //=================分割符===============
        try(Scanner s = new Scanner(System.in)){
            String next = s.next();
            System.out.println(next);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
