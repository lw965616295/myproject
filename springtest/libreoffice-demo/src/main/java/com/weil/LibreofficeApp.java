package com.weil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName LibreofficeApp
 * @Author weil
 * @Description //启动类
 * @Date 2024/08/05 11:08
 * @Version 1.0.0
 **/
@SpringBootApplication
@EnableScheduling
public class LibreofficeApp {
    public static void main(String[] args) {
        SpringApplication.run(LibreofficeApp.class, args);
    }
}
