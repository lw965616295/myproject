package com.weil.de;

import java.util.Random;

/**
 * @ClassName RandomDemo
 * @Author weil
 * @Description //随机数
 * @Date 2021/9/3 11:34
 * @Version 1.0.0
 **/
public class RandomDemo {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(10));
        }
        System.out.println("分割-----");
        for (int i = 0; i < 10; i++) {
            System.out.println((int)(Math.random() * 10));
        }


    }
}
