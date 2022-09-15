package com.weil.de;

/**
 * @Name: FunctionDemo
 * @Description: 函数式接口
 * @Author: weil
 * @Date: 2022-09-15 10:19
 * @Version: 1.0
 */
public class FunctionDemo {
    public static void main(String[] args) {
        // 函数式编程最终还是生成匿名内部类的实现

        MyFunction function = ()-> System.out.println("hello world!");
        function.say();

        // 单行return 可以省略
        MyFunction2 function2 = (a, b)-> a+b;
        System.out.println(function2.calc(2, 4));
    }


    @FunctionalInterface
    interface MyFunction {
        void say();
    }

    @FunctionalInterface
    interface MyFunction2 {
        Integer calc(Integer a, Integer b);
    }
}
