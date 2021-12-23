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
}
