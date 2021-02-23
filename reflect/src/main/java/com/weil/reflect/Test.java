/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.reflect;

import com.weil.reflect.bean.Student;
import com.weil.reflect.proxy.Animal;
import com.weil.reflect.proxy.Dog;
import com.weil.reflect.proxy.MyProxy;

import java.lang.reflect.*;


/**
 * @ClassName Test
 * @Author liwei
 * @Description //TODO
 * @Date 20:54 2020/5/19
 * @Version 1.0.0
 **/
public class Test {
    public static void main(String[] args) throws Exception{
        // 创建对象阶段
        Student student = new Student();
        Class<? extends Student> clazz1 = student.getClass();
        // 字节码阶段
        Class<Student> clazz2 = Student.class;
        // 源文件阶段
        Class<?> clazz3 = Class.forName("com.weil.reflect.bean.Student");
        System.out.println(clazz1 == clazz2);
        System.out.println(clazz1 == clazz3);

        // 该方法需要该类拥有无参构造方法
        Student student1 = Student.class.newInstance();

        Constructor<? extends Student> constructor = clazz1.getConstructor(String.class, int.class);
        Student stu = constructor.newInstance("weil", 10);
        System.out.println(stu);

        // 操作field
        Field[] fields = clazz1.getFields();
        System.out.println("---->打印field:");
        // 只能打印public的
        for (Field field : fields) {
            System.out.println(field.getName());
        }


//        Field name = clazz1.getField("name");
        // 暴力反射
        Field name = clazz1.getDeclaredField("name");
        name.setAccessible(true);
        Object o = name.get(stu);
        System.out.println(o);

        // 操作method
        Method[] methods = clazz1.getMethods();
        System.out.println("----->打印method:");
        for (Method method : methods) {
            System.out.println(method.getName());
        }
        // 执行方法
        System.out.println("----->执行method:");
        Method toString = clazz1.getMethod("toString");
        Object invoke = toString.invoke(stu);
        System.out.println(invoke);

        // 动态代理
        Dog dog = Dog.class.newInstance();
        dog.eat();
        Animal d1 = (Animal) Proxy.newProxyInstance(dog.getClass().getClassLoader(), dog.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("---吃饭前");
                method.invoke(proxy, args);
                System.out.println("---吃饭后");
                return proxy;
            }
        });
        d1.eat();
        // 自定义动态代理
//        MyProxy myProxy = new MyProxy(dog);
//        myProxy.invoke(Dog.class, "eat");

    }
}
