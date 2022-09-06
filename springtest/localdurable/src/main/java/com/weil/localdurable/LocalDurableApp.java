package com.weil.localdurable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Name: LocalDurableApp
 * @Description: 启动类
 * @Author: weil
 * @Date: 2022-09-06 10:07
 * @Version: 1.0
 */
@SpringBootApplication
public class LocalDurableApp {
    public static void main(String[] args) {
        SpringApplication.run(LocalDurableApp.class, args);
    }
}
