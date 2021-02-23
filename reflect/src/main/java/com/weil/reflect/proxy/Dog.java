/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.reflect.proxy;

/**
 * @ClassName Dog
 * @Author liwei
 * @Description //TODO
 * @Date 20:50 2020/6/1
 * @Version 1.0.0
 **/
public class Dog implements Animal{
    public String eat() {
        System.out.println("eat eggs!");
        return "eat egg!";
    }
}
