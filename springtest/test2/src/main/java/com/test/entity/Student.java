/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.test.entity;

/**
 * @ClassName Student
 * @Author liwei
 * @Description //TODO
 * @Date 17:07 2020/5/18
 * @Version 1.0.0
 **/
public class Student {
    private String name;
    private Boolean sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }
}
