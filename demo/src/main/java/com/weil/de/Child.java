package com.weil.de;

import com.weil.de.entity.Dog;
import lombok.Data;

/**
 * @ClassName Child
 * @Author weil
 * @Description //测试clone类
 * @Date 2021/6/15 14:39
 * @Version 1.0.0
 **/
@Data
public class Child implements Cloneable{
    private String name;
    private String age;
    private Dog dog;
    String eat(){
        System.out.println("d");
        return "d";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
