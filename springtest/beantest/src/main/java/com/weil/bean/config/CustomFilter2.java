package com.weil.bean.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName CustomFilter
 * @Author weil
 * @Description //自定义过滤器2
 * @Date 2022/2/21 10:32
 * @Version 1.0.0
 **/
@Component
@Slf4j
public class CustomFilter2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化过滤器2：{}", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        log.info("requestURI2:{}", requestURI);
        long start = System.currentTimeMillis();
        // 过滤链往下走
        filterChain.doFilter(servletRequest, servletResponse);
        // 程序处理完响应
        long end = System.currentTimeMillis();
        log.info("用户请求处理完，持续2：{}", end-start);
    }

    @Override
    public void destroy() {
        log.info("过滤器销毁2");
    }
}
