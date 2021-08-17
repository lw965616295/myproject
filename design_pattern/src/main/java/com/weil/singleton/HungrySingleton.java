package com.weil.singleton;

/**
 * @ClassName HungrySingleton
 * @Author weil
 * @Description //饿汉式创建
 * @Date 2021/8/16 17:29
 * @Version 1.0.0
 **/
public class HungrySingleton {
    // 单例总体思想：类自己创建对象，并私有化构造，提供公共获取方法
    private static HungrySingleton singleton = new HungrySingleton();
    // 私有化构造，防止外界直接创建对象
    private HungrySingleton(){}
    // 提供公共方法获取对象
    public HungrySingleton getInstance(){
        return singleton;
    }
}
