/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.selector.config;

import com.weil.selector.bean.Monkey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName Config1
 * @Author liwei
 * @Description //TODO
 * @Date 16:59 2020/5/20
 * @Version 1.0.0
 **/
@Configuration
public class Config1 {

    @Bean
    public Monkey getMonkey(){
        Monkey jj = new Monkey("jj", 10);
        System.out.println(jj);
        return jj;
    }
}
