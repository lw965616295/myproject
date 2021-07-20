package com.weil.de;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ListTest
 * @Author weil
 * @Description //集合测试
 * @Date 2021/7/19 15:09
 * @Version 1.0.0
 **/
public class ListTest {
    public static void main(String[] args) {
        // 初始化50个值
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            list.add(i);
        }
        // 开一个线程单独跑添加值
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 500; i < 1000; i++) {
                    // 延时
                    if(i==505){
                        Thread.sleep(1);
                    }
                    list.add(i);
                }
            }
        }).start();
        // 当前集合在添加数据的时候，如果进行<遍历同时>删除数据操作，会触发checkForComodification异常
        //Exception in thread "main" java.util.ConcurrentModificationException
        //	at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
        //	at java.util.ArrayList$Itr.next(ArrayList.java:851)
        //	at com.weil.de.ListTest.main(ListTest.java:35)
//        for (Integer l : list) {
//            System.out.println("list size:"+ list.size());
//            System.out.println("l:"+l);
//            list.remove(l);
//        }
        // 正确的做法，先新建一个集合暂存，再遍历新集合，再用老集合删除
        List<Integer> list2 = new ArrayList<>(list);
        for (Integer l : list2) {
            System.out.println("list2 size:"+ list2.size());
            System.out.println("list size:"+ list.size());
            System.out.println("l:"+l);
            list.remove(l);
        }

    }
}
