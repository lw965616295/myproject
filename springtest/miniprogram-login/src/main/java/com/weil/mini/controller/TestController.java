package com.weil.mini.controller;

import com.weil.mini.config.AuthConfig;
import com.weil.mini.login.AlipayLogin;
import com.weil.mini.login.MiniLogin;
import com.weil.mini.login.WechatLogin;
import com.weil.mini.model.AuthParams;
import com.weil.mini.model.LoginRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name: TestController
 * @Description: 测试
 * @Author: weil
 * @Date: 2022-08-31 15:11
 * @Version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    AuthConfig authConfig;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @RequestMapping("/test/wechat")
    public LoginRes wechat(@RequestParam String code){
        System.out.println("进入。。。wechat");
        MiniLogin miniLogin = new WechatLogin(authConfig);
        LoginRes loginRes = miniLogin.login(AuthParams.builder().authCode(code).build());

        return loginRes;
    }
    @RequestMapping("/test/alipay")
    public LoginRes alipay(@RequestParam String code){
        System.out.println("进入。。。alipay");
        MiniLogin miniLogin = new AlipayLogin(authConfig);
        LoginRes loginRes = miniLogin.login(AuthParams.builder().authCode(code).build());

        return loginRes;
    }
    @GetMapping("/test/redis")
    public String testRedis(){
        stringRedisTemplate.opsForValue().set("aa","bb");
//        HashMap<String, Object> map = new HashMap();
//        map.put("name", "weil");
//        map.put("age", 12);
//        stringRedisTemplate.opsForHash().putAll("jwt:userId:"+"12", map);
        // 使用putAll有java.lang.Integer cannot be cast to java.lang.String 异常
        stringRedisTemplate.opsForHash().put("jwt:userId:"+"12", "name", "weil");
        stringRedisTemplate.opsForHash().put("jwt:userId:"+"12", "age", "14");
        return null;
    }
}
