/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.reflect;

import com.weil.reflect.bean.Student;
import com.weil.reflect.proxy.Animal;
import com.weil.reflect.proxy.Dog;
import com.weil.reflect.proxy.MyProxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.*;


/**
 * @ClassName TestDemo
 * @Author liwei
 * @Description //测试
 * @Date 20:54 2020/5/19
 * @Version 1.0.0
 **/
public class TestDemo {
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



    }
    /**
     * 测试动态代理
     */
    @Test
    public void testProxy() throws Exception{
        // 动态代理
        Dog dog = Dog.class.newInstance();
        dog.eat();
        Animal d1 = (Animal) Proxy.newProxyInstance(dog.getClass().getClassLoader(), dog.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("---吃饭前");
                Object invoke = method.invoke(dog, args);
                System.out.println("---吃饭后");
                return invoke;
            }
        });
        d1.eat();
    }

    /**
     * 测试自定义动态代理
     */
    @Test
    public void testProxy1(){
        try {
            // 自定义动态代理
            Dog dog = new Dog();
            MyProxy myProxy = new MyProxy(dog);
            myProxy.invoke(Dog.class, "eat");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * 测试cglib代理
     * 该方法可以允许被代理的类不用实现接口
     */
    @Test
    public void testCglib(){
        Enhancer enhancer = new Enhancer();
        // 设置类加载器
        enhancer.setClassLoader(Dog.class.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(Dog.class);
        // 设置处理
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("吃饭前---");
                Object invoke = methodProxy.invokeSuper(o, objects);
                System.out.println("吃饭后---");
                return invoke;
            }
        });
        Dog dog = (Dog) enhancer.create();
        dog.eat();
    }
}
