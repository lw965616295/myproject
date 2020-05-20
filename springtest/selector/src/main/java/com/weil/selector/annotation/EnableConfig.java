/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.selector.annotation;

import com.weil.selector.config.Config1;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName EnableConfig
 * @Author liwei
 * @Description //TODO
 * @Date 17:16 2020/5/20
 * @Version 1.0.0
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(Config1.class)
public @interface EnableConfig {
}
