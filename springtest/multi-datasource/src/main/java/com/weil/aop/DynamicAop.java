package com.weil.aop;

import com.weil.annotation.DS;
import com.weil.common.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Name: DynamicAop
 * @Description:
 * @Author: weil
 * @Date: 2022-10-10 20:16
 * @Version: 1.0
 */
@Aspect
@Component
public class DynamicAop {

    /**
     * 切面
     * @within(inner) 注解在类上
     * @annotation(inner) 注解在方法上
     */
    @Around("@within(ds) || @annotation(ds)")
    public Object around(ProceedingJoinPoint joinPoint, DS ds) throws Throwable{
        ds = getDs(joinPoint);
        // 先设置数据源
        DynamicDataSourceContextHolder.setContext(ds.value());
        // 方法放行
        Object proceed = joinPoint.proceed();
        // 清除数据源
        DynamicDataSourceContextHolder.removeContext();
        return proceed;

    }

    /**
     * 获取注解
     * 如果类上和方法上都有注解，则已方法上的为准
     */
    private DS getDs(ProceedingJoinPoint joinPoint) {
        // 先判断方法的注解
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DS ds = methodSignature.getMethod().getAnnotation(DS.class);
        if(ds == null){
            // 方法上没有注解，则使用类上的
            Class<?> targetClass = joinPoint.getTarget().getClass();
            ds = targetClass.getAnnotation(DS.class);
        }
        return ds;
    }
}
