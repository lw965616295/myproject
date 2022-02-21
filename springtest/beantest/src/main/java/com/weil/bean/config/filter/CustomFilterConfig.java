package com.weil.bean.config.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @ClassName CustomFilterConfig
 * @Author weil
 * @Description //自定义过滤器配置注册
 * @Date 2022/2/21 13:32
 * @Version 1.0.0
 **/
@Configuration
public class CustomFilterConfig {
    @Autowired
    private CustomFilter filter;
    @Autowired
    private CustomFilter2 filter2;
    @Bean
    public FilterRegistrationBean<CustomFilter> thirdFilter(){
        FilterRegistrationBean<CustomFilter> bean = new FilterRegistrationBean<>();
        // 定义执行顺序
        bean.setOrder(2);
        bean.setFilter(filter);
        bean.setUrlPatterns(new ArrayList<>(Arrays.asList("/test")));
        return bean;
    }
    @Bean
    public FilterRegistrationBean<CustomFilter2> thirdFilter2(){
        FilterRegistrationBean<CustomFilter2> bean = new FilterRegistrationBean<>();
        bean.setOrder(1);
        bean.setFilter(filter2);
        bean.setUrlPatterns(new ArrayList<>(Arrays.asList("/test")));
        return bean;
    }
}
