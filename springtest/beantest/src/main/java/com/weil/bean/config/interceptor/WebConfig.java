package com.weil.bean.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebConfig
 * @Author weil
 * @Description //注册拦截器
 * @Date 2022/2/22 16:23
 * @Version 1.0.0
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器和拦截路径
        registry.addInterceptor(new CustomInterceptor()).addPathPatterns("/test");
    }
}
