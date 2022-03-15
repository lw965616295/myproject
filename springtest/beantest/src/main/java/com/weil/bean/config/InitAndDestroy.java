package com.weil.bean.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName InitAndDestroy
 * @Author weil
 * @Description //旧版初始化和销毁操作
 * @Date 2022/3/15 13:57
 * @Version 1.0.0
 **/
@Configuration
@Slf4j
public class InitAndDestroy {
    public InitAndDestroy() {
       log.info("InitAndDestroy 构造函数执行...");
    }
    @PostConstruct
    private void init(){
        log.info("InitAndDestroy 初始化程序执行...");
    }
    @PreDestroy
    private void destroy(){
        log.info("InitAndDestroy 销毁程序执行...");
    }
}
