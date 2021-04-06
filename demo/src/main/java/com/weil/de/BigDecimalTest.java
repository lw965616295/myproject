/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.de;

import com.weil.de.entity.Cat;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BigDecimalTest
 * @Author liwei
 * @Description //TODO
 * @Date 9:48 2021/3/17
 * @Version 1.0.0
 **/
public class BigDecimalTest {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("3.10");
        BigDecimal b = new BigDecimal("2");
        BigDecimal c = new BigDecimal("2");
        BigDecimal divide = a.multiply(b).divide(c,2,BigDecimal.ROUND_HALF_UP);
        //方法一
        BigDecimal bigDecimal = divide.stripTrailingZeros();
        System.out.println(bigDecimal.toPlainString());
        //方法二
        DecimalFormat df = new DecimalFormat("########.##");
        String format = df.format(divide);
        System.out.println(format);


        List<String> cc = new ArrayList<>();
        Cat cat = new Cat();
        cat.setCc(cc);
        List<String> cc1 = cat.getCc();
        cc1.add("dd");
        System.out.println(cc.size());

        int kk = '1';
        System.out.println(kk);
        int dd = 1;
        System.out.println(dd);
    }
}
