/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.selector;

import com.weil.selector.annotation.EnableConfig;
import com.weil.selector.bean.Monkey;
import com.weil.selector.config.Config1;
import com.weil.selector.selector.MySelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * @ClassName SelectorApp
 * @Author liwei
 * @Description //TODO
 * @Date 16:55 2020/5/20
 * @Version 1.0.0
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.weil.selector.bean"})
@Import(MySelector.class)
@EnableConfig(name="abc")
public class SelectorApp {
    @Autowired
    Monkey monkey;
    public static void main(String[] args) {
        SpringApplication.run(SelectorApp.class, args);
    }

    @Bean
    public String aa(){
        System.out.println("----->:");
        System.out.println(monkey);
        return "abc";
    }
}
