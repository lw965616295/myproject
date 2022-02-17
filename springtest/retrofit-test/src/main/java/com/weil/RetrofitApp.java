package com.weil;

import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName RetrofitApp
 * @Author weil
 * @Description ///启动类
 * @Date 2022/2/17 9:59
 * @Version 1.0.0
 **/
@SpringBootApplication
@RetrofitScan("com.weil.service")
public class RetrofitApp {
    public static void main(String[] args) {
        SpringApplication.run(RetrofitApp.class, args);
    }
}
