package com.weil.test;

import com.weil.bean.Book;
import com.weil.bean.User;
import com.weil.bean.UserDto;
import com.weil.convert.MapStructConvert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName BeanConvertTest
 * @Author weil
 * @Description //测试类
 * @Date 2022/2/28 10:17
 * @Version 1.0.0
 **/
public class BeanConvertTest {
    User user = new User();
    @Before
    public void before(){
        user.setId(123L);
        user.setName("小李");
        List<Book> books = new ArrayList<>();
        books.add(new Book("红楼梦", 22));
        books.add(new Book("西游记", 32));
        user.setBooks(books);
    }
    @Test
    public void test1(){
        UserDto userDto = MapStructConvert.INSTANCE.toUserDto(user);
        System.out.println(userDto);
        List<Book> books = user.getBooks();
        List<Book> books1 = userDto.getBooks();
        System.out.println(books.hashCode());
        System.out.println(books1.hashCode());
    }
}
