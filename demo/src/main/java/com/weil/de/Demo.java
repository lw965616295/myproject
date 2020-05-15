package com.weil.de;

import com.weil.de.entity.Cat;
import com.weil.de.entity.Dog;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName Demo
 * @Author liwei
 * @Description //TODO
 * @Date 18:17 2020/4/15
 * @Version 1.0.0
 **/
public class Demo {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.setAge(12);
        cat.setName("mi");
        Dog dog = new Dog();
        try {
            BeanUtils.copyProperties(dog, cat);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(dog);
    }
}
