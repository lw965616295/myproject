package com.weil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.ServletOutputStream;

/**
 * @ClassName ExceptionTest
 * @Author weil
 * @Description //异常测试---测试类
 * @Date 2022/2/18 17:05
 * @Version 1.0.0
 **/
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class ExceptionTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void test1() throws Exception {
        // todo 不知道怎么玩。。。待定
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test2")).andReturn();
    }
}
