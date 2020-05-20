/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.reflect.bean;

/**
 * @ClassName Student
 * @Author liwei
 * @Description //TODO
 * @Date 20:55 2020/5/19
 * @Version 1.0.0
 **/
public class Student {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
