package com.weil.netty.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Student
 * @Author weil
 * @Description //出入对象测试类
 * @Date 2021/7/1 14:05
 * @Version 1.0.0
 **/

@Data
public class Student implements Serializable {
    private String name;
    private Integer age;
    private List<String> books;
}
