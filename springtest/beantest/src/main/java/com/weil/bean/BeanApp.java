package com.weil.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName BeanApp
 * @Author weil
 * @Description //启动类
 * @Date 2021/12/23 13:52
 * @Version 1.0.0
 **/
@SpringBootApplication
@EnableScheduling
public class BeanApp {
    public static void main(String[] args) {
        SpringApplication.run(BeanApp.class, args);
    }
}
