package com.weil.bean;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @ClassName UserDto
 * @Author weil
 * @Description //用户dto
 * @Date 2022/2/28 10:33
 * @Version 1.0.0
 **/
@Data
@ToString
public class UserDto {
    private String UID;
    private String name;
    private List<Book> books;

}
