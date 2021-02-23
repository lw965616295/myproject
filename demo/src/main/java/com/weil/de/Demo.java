package com.weil.de;

import com.weil.de.entity.Cat;
import com.weil.de.entity.Dog;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName Demo
 * @Author liwei
 * @Description //TODO
 * @Date 18:17 2020/4/15
 * @Version 1.0.0
 **/
public class Demo {
    public static void main(String[] args) {
//        copy();
//        testInteger();
//        testCalendar();
//        testTime1();
        testList();
//        testSet();
//        testArray();
//        testLsit();
//        testMap();
//        testInt();
//        testBigDecimal();
    }

    private static void testBigDecimal() {
        BigDecimal divide = BigDecimal.ONE.divide(BigDecimal.valueOf(1),2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(divide);
    }

    private static void copy() {
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
    private static void testInteger(){
        Integer i = 2;
        Integer a = 1;
        System.out.println(i>a);
        System.out.println(i.compareTo(1));
    }
    private static void testCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1+1);
        Date time = calendar.getTime();
        System.out.println(time);
    }
    private static void testTime1(){
        Double d = 0.5d;
        double v = d * 60 * 60;
        long a = 1000L;
        double v1 = v + a;
        long l = Double.doubleToRawLongBits(v1);
        System.out.println(l);
    }
    private static void testList(){
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(null);
        ids.add(null);
        ids.add(1L);
        System.out.println(ids.size());
        System.out.println(ids.toString());
    }
    private static void testSet(){
        Set<Long> ids = new HashSet<Long>();
        ids.add(1L);
        ids.add(null);
        ids.add(1L);
        System.out.println(ids.size());
        System.out.println(ids.toString());
    }
    private static void testArray(){
        String[] strs = {null,null};

        System.out.println(strs.length);
        System.out.println(strs[0]+"      "+strs[1]);
    }
    private static void testLsit(){
        List<String> strs = new ArrayList<String>();
        strs.add("1");
        System.out.println(strs.size()==0);
        System.out.println(strs.isEmpty());
    }
    private static void testMap(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "weil");
        map.put("name", "weil2");
        map.put(null, "aa");
        map.put(null, "aa2");
        System.out.println(map.size());
        System.out.println(map.get(null));
    }
    private static void testInt(){
        int i = 5/3;
        System.out.println(i);
        //该函数只对浮点数有效
        int ceil = (int)Math.ceil((double)6/3);
        System.out.println(ceil);

    }
}
