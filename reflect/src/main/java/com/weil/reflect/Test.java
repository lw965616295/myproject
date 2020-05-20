/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.reflect;

import com.weil.reflect.bean.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @ClassName Test
 * @Author liwei
 * @Description //TODO
 * @Date 20:54 2020/5/19
 * @Version 1.0.0
 **/
public class Test {
    public static void main(String[] args) throws Exception{
        Student student = new Student();
        Class<? extends Student> clazz1 = student.getClass();
        Class<Student> clazz2 = Student.class;
        Class<?> clazz3 = Class.forName("com.weil.reflect.bean.Student");
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz1 == clazz3);

        // 该方法需要该类拥有无参构造方法
        Student student1 = Student.class.newInstance();

        Constructor<? extends Student> constructor = clazz1.getConstructor(String.class, int.class);
        Student weil = constructor.newInstance("weil", 10);
        System.out.println(weil);

        // 操作field
        Field[] fields = clazz1.getFields();
        System.out.println("---->打印field:");
        for (Field field : fields) {
            System.out.println(field.getName());
        }


//        Field name = clazz1.getField("name");
        // 暴力反射
        Field name = clazz1.getDeclaredField("name");
        name.setAccessible(true);
        Object o = name.get(weil);
        System.out.println(o);

        // 操作method
        Method[] methods = clazz1.getMethods();
        System.out.println("----->打印method:");
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }
}
