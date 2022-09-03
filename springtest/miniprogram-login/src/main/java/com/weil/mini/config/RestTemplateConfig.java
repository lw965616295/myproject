package com.weil.mini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Name: RestTemplateConfig
 * @Description: 配置rest客户端
 * @Author: weil
 * @Date: 2022-08-31 15:41
 * @Version: 1.0
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    RestTemplate restTemplate(){
        return getRestTemplate();
    }
    public static RestTemplate getRestTemplate(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(60*1000);
        factory.setConnectTimeout(10*1000);
        return new RestTemplate(factory);
    }
}
