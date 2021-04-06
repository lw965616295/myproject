package com.weil.de.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName Cat
 * @Author liwei
 * @Description //TODO
 * @Date 18:19 2020/4/15
 * @Version 1.0.0
 **/
@Data
@ToString
public class Cat {
    private String name;
    private Integer age;
    List<String> cc;
}
