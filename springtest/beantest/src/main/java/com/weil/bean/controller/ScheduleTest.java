package com.weil.bean.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName ScheduleTest
 * @Author weil
 * @Description //schedule测试
 * @Date 2022/3/14 17:15
 * @Version 1.0.0
 **/
@Component
@Slf4j
//@EnableAsync  // 如果没有自定义线程池的话，这个注解会默认创建线程池给其使用  线程：SimpleAsyncTaskExecutor-1，执行了定时任务~~
public class ScheduleTest {

//    @Async       // 并行执行下面代码
    @Scheduled(fixedRate = 5000)
    public void test() throws Exception{
        Thread.sleep(1000*6);
        log.info("线程：{}，执行了定时任务~~", Thread.currentThread().getName());
    }
}
