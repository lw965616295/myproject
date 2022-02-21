package com.weil.bean.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @ClassName ExecutorService
 * @Author weil
 * @Description //测试线程池执行情况
 * @Date 2021/12/23 14:15
 * @Version 1.0.0
 **/
@Service
@Slf4j
public class ExecutorService {
    @Async("asyncExecutor")
    public void testExecutor(){
        log.info("test asyncExecutor start!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("test asyncExecutor end!");
    }
    //2022-02-18 16:15:13.818  INFO 12920 --- [weil-executor-1] com.weil.bean.service.ExecutorService    : test asyncExecutor start!
    //2022-02-18 16:15:14.821  INFO 12920 --- [weil-executor-1] com.weil.bean.service.ExecutorService    : test asyncExecutor end!
    //2022-02-18 16:15:21.360  INFO 12920 --- [weil-executor-2] com.weil.bean.service.ExecutorService    : test asyncExecutor start!
    //2022-02-18 16:15:22.370  INFO 12920 --- [weil-executor-2] com.weil.bean.service.ExecutorService    : test asyncExecutor end!
}
