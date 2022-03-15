package com.weil.bean.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @ClassName InitAndDestroy2
 * @Author weil
 * @Description //spring初始化和销毁操作
 * @Date 2022/3/15 13:57
 * @Version 1.0.0
 **/
@Configuration
@Slf4j
public class InitAndDestroy2 implements InitializingBean, DisposableBean {
    public InitAndDestroy2() {
       log.info("InitAndDestroy2 构造函数执行...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitAndDestroy2 初始化程序执行...");
    }

    @Override
    public void destroy() throws Exception {
        log.info("InitAndDestroy2 销毁程序执行...");
    }
}
