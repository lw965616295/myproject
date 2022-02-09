package com.weil.controller;

import com.weil.service.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName IndexController
 * @Author weil
 * @Description //index
 * @Date 2022/2/8 13:13
 * @Version 1.0.0
 **/
@RestController
@Slf4j
public class IndexController {
    @Autowired
    RedisLock redisLock;
    // 计数用
    private Integer count = 0;
    /**
     * 测试redis锁
     */
    @GetMapping("/test")
    public String testRedisLock(){
        // 用于等待所有线程都执行完
        CountDownLatch latch = new CountDownLatch(1000);
        // 开1000个线程来跑计数（线程开太多也不是好事，redis连接数有限，一下子加锁用了所有的连接后，就没有连接处理解锁了；所有这边设5个，其余都放队列中等待）
        Integer coreNum = 5;
        Integer maxNum = 50;
        Long delay = 1L;
        Integer queueLength = 1000;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(coreNum, maxNum, delay, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueLength), new ThreadPoolExecutor.CallerRunsPolicy());
        // 开始时间
        long start = System.currentTimeMillis();
        for (int i=0; i<1000; i++){
            executor.execute(()->{
                String id = UUID.randomUUID().toString();
                // 加锁
                try {
                    redisLock.lock(id);
                    count++;
                    log.info("当前count:{}", count);
                } finally {
                    // 解锁
                    redisLock.unlock(id);
                }
                // 执行完
                latch.countDown();
            });
        }
        try {
            // 只要是有一个线程没有执行完就一直阻塞在这里
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 持续时间
        long during = System.currentTimeMillis() - start;
        log.info("执行时间：{}ms，计数count:{}", during, count);
        return "hello";
    }
}
