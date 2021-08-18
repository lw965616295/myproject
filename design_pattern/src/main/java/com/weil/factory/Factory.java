package com.weil.factory;

/**
 * @ClassName Factory
 * @Author weil
 * @Description //工厂模式
 * @Date 2021/8/17 17:24
 * @Version 1.0.0
 **/
public class Factory {
    interface Animal{
        void eat();
    }
    static class Dog implements Animal {
        @Override
        public void eat() {
            System.out.println("狗吃骨头！");
        }
    }
    static class Cat implements Animal {
        @Override
        public void eat() {
            System.out.println("猫吃鱼！");
        }
    }
    // 工厂接口
    interface FactoryInterface{
        Animal createAnimal();
    }
    // 猫工厂
    static class CatFactory implements FactoryInterface{
        @Override
        public Animal createAnimal() {
            return new Cat();
        }
    }
    // 狗工厂
    static class DogFactory implements FactoryInterface{
        @Override
        public Animal createAnimal() {
            return new Dog();
        }
    }

    public static void main(String[] args) {
        // 在知道是哪个工厂时就可以创建
        FactoryInterface f = new CatFactory();
        f.createAnimal().eat();
        FactoryInterface a = new DogFactory();
        a.createAnimal().eat();
    }
}
