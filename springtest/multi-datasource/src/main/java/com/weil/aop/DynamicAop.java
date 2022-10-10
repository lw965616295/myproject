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
     * 切点
     */
    @Pointcut("@annotation(com.weil.annotation.DS)")
    public void dsPointCut(){}

    /**
     * 切面
     */
    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        String ds = getDs(joinPoint).value();
        // 先设置数据源
        DynamicDataSourceContextHolder.setContext(ds);
        // 方法放行
        Object proceed = joinPoint.proceed();
        // 清除数据源
        DynamicDataSourceContextHolder.removeContext();
        return proceed;

    }

    /**
     * 获取注解的值
     */
    private DS getDs(ProceedingJoinPoint joinPoint) {
        // 先判断类的注解，再判断方法的注解
        Class<?> targetClass = joinPoint.getTarget().getClass();
        DS ds = targetClass.getAnnotation(DS.class);
        if(Objects.nonNull(ds)){
            return ds;
        }else {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getMethod().getAnnotation(DS.class);
        }
    }
}
