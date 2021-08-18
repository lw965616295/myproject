package com.weil.factory;

/**
 * @ClassName SimpleFactory
 * @Author weil
 * @Description //简单工厂，工厂类提供静态方法，用户只要提供名称就能创建对应对象，每次调用都是新的
 * 简单工厂类只有一个，负责所有产品的创建，导致冗杂，不易维护；每次增加产品都会修改工厂方法
 * 工厂模式是在其基础上对工厂的抽象，不同工厂负责不同产品
 * @Date 2021/8/17 15:27
 * @Version 1.0.0
 **/
public class SimpleFactory {
    interface Animal{
        void eat();
    }
    static class Dog implements Animal{
        @Override
        public void eat() {
            System.out.println("狗吃骨头！");
        }
    }
    static class Cat implements Animal{
        @Override
        public void eat() {
            System.out.println("猫吃鱼！");
        }
    }
    static Animal getAnimal(String name){
        if("cat".equals(name)){
            return new Cat();
        }else if("dog".equals(name)){
            return new Dog();
        }else {
            return null;
        }
    }

    public static void main(String[] args) {
        Animal cat = getAnimal("cat");
        cat.eat();
        Animal dog = getAnimal("dog");
        dog.eat();
        Animal cat1 = getAnimal("cat");
        System.out.println("cat==cat1?"+(cat==cat1));
    }
}
