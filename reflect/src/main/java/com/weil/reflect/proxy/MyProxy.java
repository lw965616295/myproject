/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.reflect.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName MyProxy
 * @Author liwei
 * @Description //自定义动态代理---简易
 * @Date 21:16 2020/6/1
 * @Version 1.0.0
 **/
public class MyProxy {
    private Object obj;
    public MyProxy(Object object){
        obj = object;
    }
    public void invoke(Class clazz, String method, Class<?>... strings) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = clazz.getDeclaredMethod(method, strings);
        declaredMethod.setAccessible(true);
        System.out.println("---执行开始！");
        Object invoke = declaredMethod.invoke(obj);
        System.out.println("----执行结束！");

    }

}
