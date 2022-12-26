package com.weil.limit.aop;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import com.weil.limit.annotation.GuavaLimit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Name: GuavaLimitAop
 * @Description:
 * @Author: weil
 * @Date: 2022-12-26 11:10
 * @Version: 1.0
 */
@Slf4j
@Aspect
@Component
public class GuavaLimitAop {

    /**
     * 不同的接口，不同的流量控制
     * map的key为 Limiter.key
     */
    private final Map<String, RateLimiter> limitMap = Maps.newConcurrentMap();

    @Around("@annotation(com.weil.limit.annotation.GuavaLimit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取limit的注解
        GuavaLimit limit = method.getAnnotation(GuavaLimit.class);
        if (limit != null) {
            //key作用：不同的接口，不同的流量控制
            String key=limit.key();
            RateLimiter rateLimiter = null;
            //验证缓存是否有命中key
            if (!limitMap.containsKey(key)) {
                // 创建令牌桶
                rateLimiter = RateLimiter.create(limit.permits());
                limitMap.put(key, rateLimiter);
                log.info("新建了令牌桶={}，容量={}", key, limit.permits());
            }
            rateLimiter = limitMap.get(key);
            // 获取令牌
            boolean acquire = rateLimiter.tryAcquire(limit.timeout(), limit.timeunit());
            // 获取不到命令，直接返回异常提示
            if (!acquire) {
                log.debug("令牌桶={}，获取令牌失败", key);
                throw new RuntimeException(limit.msg());
            }
        }
        return joinPoint.proceed();
    }

}
