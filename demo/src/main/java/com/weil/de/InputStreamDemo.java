/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.de;

import java.util.Scanner;

/**
 * @ClassName InputStreamDemo
 * @Author liwei
 * @Description //TODO
 * @Date 9:52 2021/2/25
 * @Version 1.0.0
 **/
public class InputStreamDemo {
    public static void main(String[] args) {
        System.out.println("》》》》》》weil操作系统开启《《《《《《");
        String end = "end";
        boolean flag = true;
        while(flag){
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            System.out.println(next);
            if(next.equals(end)){
                System.out.println("》》》》》》weil操作系统关闭《《《《《《");
            }
        }

    }
}
