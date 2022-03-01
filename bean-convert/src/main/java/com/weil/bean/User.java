package com.weil.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName User
 * @Author weil
 * @Description //用户类
 * @Date 2022/2/28 10:30
 * @Version 1.0.0
 **/
@Data
@ToString
public class User {
    private Long id;
    private String name;
    private List<Book> books;
}
