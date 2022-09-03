package com.weil.mini;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weil.mini.mapper")
public class MiniProgramLoginApp {
    public static void main(String[] args) {
        SpringApplication.run(MiniProgramLoginApp.class, args);
    }
}
