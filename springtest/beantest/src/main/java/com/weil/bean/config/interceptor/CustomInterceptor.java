package com.weil.bean.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CustomInterceptor
 * @Author weil
 * @Description //自定义拦截器
 * 自定义 Interceptor 的话必须实现 org.springframework.web.servlet.HandlerInterceptor接口或继承 org.springframework.web.servlet.handler.HandlerInterceptorAdapter类，并且需要重写下面下面3个方法：
 * @Date 2022/2/22 16:01
 * @Version 1.0.0
 **/
@Slf4j
public class CustomInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("interceptor preHandle~~");
        long start = System.currentTimeMillis();
        log.info("start time:{}", start);
        request.setAttribute("time", start);
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("interceptor postHandle~~");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("interceptor afterCompletion~~");
        Long startTime = (Long) request.getAttribute("time");
        long during = System.currentTimeMillis() - startTime;
        log.info("拦截持续：{}", during);
    }
}
