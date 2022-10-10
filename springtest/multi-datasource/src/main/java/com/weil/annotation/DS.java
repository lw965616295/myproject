package com.weil.annotation;

import com.weil.common.DynamicConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Name: DS
 * @Description: 选择数据源注解
 * @Author: weil
 * @Date: 2022-10-10 20:11
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DS {
    /**
     * 数据名称
     */
    String value() default DynamicConstants.DS_MASTER;
}
