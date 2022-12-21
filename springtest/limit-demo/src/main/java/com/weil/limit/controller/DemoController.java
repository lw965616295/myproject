package com.weil.limit.controller;

import com.weil.limit.annotation.RedisLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name: DemoController
 * @Description:
 * @Author: weil
 * @Date: 2022-11-22 20:21
 * @Version: 1.0
 */
@RestController
public class DemoController {
    @RedisLimit(key = "redis-limit:test", permits = 2, expire = 1, msg = "当前排队人数较多，请稍后再试！")
    @GetMapping("/test")
    public String test(){
        System.out.println("haha");
        return "haha";
    }
}
