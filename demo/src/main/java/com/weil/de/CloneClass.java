package com.weil.de;

import com.weil.de.entity.Dog;

/**
 * @ClassName CloneClass
 * @Author weil
 * @Description //克隆测试
 * @Date 2021/6/15 14:26
 * @Version 1.0.0
 **/
public class CloneClass{
    public static void main(String[] args) throws Exception{
        //克隆的对象需要实现cloneable接口，如果直接复写object中的clone方法而不实现接口会抛异常（clone方法没有实现）
        Child c = new Child();
        c.setAge("10");
        c.setName("ji");
        Child c1 = (Child)c.clone();
        System.out.println(c==c1);
        System.out.println(c1.getName()+c1.getAge());

        Dog dog = new Dog();
        dog.setName("dd");
        c1.setDog(dog);
        Child c2 = (Child)c.clone();
        System.out.println(dog==c2.getDog());
        System.out.println(c2.getDog().getName());

        /*
        false
        ji10
        false
        Exception in thread "main" java.lang.NullPointerException
            at com.weil.de.CloneClass.main(CloneClass.java:27)
            只拷贝值，浅拷贝；对于对象就不能拷贝，空指针异常
	 */
    }
}
