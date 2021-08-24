package com.weil.de;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName MapTest
 * @Author weil
 * @Description //map测试
 * @Date 2021/8/20 9:22
 * @Version 1.0.0
 **/
public class MapTest {
    public static void main(String[] args) {
        Map<String, String> m1 = new HashMap<>();
        Map<String, String> m2 = new HashMap<>();
        m1.put("a","1");
        m1.put("b","2");
        m2.put("a","3");
        m2.put("c","4");
        m1.putAll(m2);
        System.out.println(m1);
        //{a=3, b=2, c=4}
        //相同key，后者覆盖前者value

    }
}
