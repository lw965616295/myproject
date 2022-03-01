package com.weil.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName Book
 * @Author weil
 * @Description //书
 * @Date 2022/2/28 10:49
 * @Version 1.0.0
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String name;
    private Integer price;
}
