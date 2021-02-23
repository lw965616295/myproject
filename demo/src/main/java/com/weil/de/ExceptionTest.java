/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.de;

/**
 * @ClassName ExceptionTest
 * @Author liwei
 * @Description //TODO
 * @Date 19:02 2020/6/4
 * @Version 1.0.0
 **/
public class ExceptionTest {
    public static void main(String[] args) {
        try {
            int a =1/0;
            System.out.println("111");
        }catch (Exception e){
            System.out.println("2");
        }finally {
            System.out.println("----finally");
        }
    }
}
