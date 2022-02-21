package com.weil.bean.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName ExecutorConfig
 * @Author weil
 * @Description //线程池配置
 * @Date 2021/12/23 13:54
 * @Version 1.0.0
 **/
@EnableAsync
@Slf4j
@Configuration
public class ExecutorConfig {
    @Bean
    public Executor asyncExecutor(){
        log.info("asyncExecutor start...");
        int cpu = Runtime.getRuntime().availableProcessors();
        Integer coreNum = cpu +1;
        Integer maxNum = 2*cpu;
        Integer queueNum = 50;
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(coreNum);
        executor.setMaxPoolSize(maxNum);
        executor.setQueueCapacity(queueNum);
        executor.setThreadNamePrefix("weil-executor-");
        // 线程达到最大，策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }
}
