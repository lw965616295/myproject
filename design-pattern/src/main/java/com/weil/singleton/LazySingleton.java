package com.weil.singleton;

/**
 * @ClassName LazySingleton
 * @Author weil
 * @Description //懒汉式创建
 * @Date 2021/8/17 9:18
 * @Version 1.0.0
 **/
public class LazySingleton {
    // 开始不创建对象，在使用时创建；volatile作用：多线程时共享数据
    private static volatile LazySingleton singleton = null;
    // 私有化构造
    private LazySingleton(){}
    // 提供公共获取方法，需要加上锁，防止多次歘创建导致线程获取的对象不同
    public static synchronized LazySingleton getInstance(){
        if(singleton == null){
            singleton = new LazySingleton();
        }
        return singleton;
    }

}
