package com.weil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisDemo
 * @Author weil
 * @Description //TODO
 * @Date 2022/1/26 13:51
 * @Version 1.0.0
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisDemo {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void testRedisTemplate(){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("a","10");
        String a = ops.get("a");
        System.out.println(a);
        // 类似setnx命令
        ops.setIfAbsent("he", "123");
        // 设置key超时时间
        stringRedisTemplate.expire("he", 10, TimeUnit.SECONDS);
    }

}
