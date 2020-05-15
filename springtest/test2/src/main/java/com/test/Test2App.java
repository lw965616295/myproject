/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.test;

import com.test1.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName Test2App
 * @Author liwei
 * @Description //TODO
 * @Date 11:39 2020/5/8
 * @Version 1.0.0
 **/
@SpringBootApplication
@ComponentScan({"com.test1.config","com.test"})
//@EnableStudent
public class Test2App {

    @Autowired
    Student student;
    public static void main(String[] args) {
        SpringApplication.run(Test2App.class, args);
    }
    @Bean
    public String aa(){

        System.out.println(student.getName());
        return student.getName();
    }
}
