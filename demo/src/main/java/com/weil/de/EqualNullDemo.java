/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.de;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName EqualNullDemo
 * @Author liwei
 * @Description //TODO
 * @Date 21:32 2021/2/25
 * @Version 1.0.0
 **/
public class EqualNullDemo {
    public static void main(String[] args) {
        //为null的引用，调用静态方法不存在空指针异常
//        EqualNullDemo d = null;
//        d.ff();
//        testFloat();
//        testArrayToList();
        testList();
    }
    static void ff(){
        System.out.println("调用成功！");
    }

    static void testFloat(){
        float f1 = 1.0f-0.9f;
        float f2 = 0.9f-0.8f;
        //0.100000024
        System.out.println(f1);
        //0.099999964
        System.out.println(f2);
        //false
        System.out.println(f1==f2);
    }
    static void testArrayToList(){
        int[] nums = {1,2,3};
        List ints = Arrays.asList(nums);
        System.out.println(ints.getClass());
        System.out.println(nums.length);
        ArrayList objects = new ArrayList<>(Arrays.asList("a", "b", "c"));
    }
    static void testList(){
        //遍历集合删除元素需要使用iterator迭代器进行remove;不能使用集合方式，否则出现
        //ConcurrentModificationException同步校验异常；
        //以下操作能正常执行是因为删除的是倒数第二个数，游标cursor（从0开始每执行一个next方法则加一）和集合size相等了（倒数第二个，此时集合也少了一个了），循环结束
        //不用再比较modCount数，就避免了不一致抛异常的问题
        List<String> strs = new ArrayList<>();
        strs.add("1");
        strs.add("2");
        strs.add("3");
        strs.add("4");
        for (String str : strs) {
            System.out.println(str);
            if("3".equals(str)){
                strs.remove(str);

            }
        }
        System.out.println("-------");
        System.out.println(strs.size());
        for (String str : strs) {
            System.out.println(str);
        }
    }
}
