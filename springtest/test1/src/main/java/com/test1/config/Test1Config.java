/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.test1.config;

import com.test1.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName Test1Config
 * @Author liwei
 * @Description //TODO
 * @Date 11:33 2020/5/8
 * @Version 1.0.0
 **/
@Configuration
public class Test1Config {

    @Bean
    public Student student(){
        Student student = new Student();
        student.setName("abc");
        return student;
    }
}
