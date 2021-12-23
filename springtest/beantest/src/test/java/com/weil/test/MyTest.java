package com.weil.test;

import com.weil.bean.BeanApp;
import com.weil.bean.service.ExecutorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName MyTest
 * @Author weil
 * @Description //测试用例
 * @Date 2021/12/23 14:19
 * @Version 1.0.0
 **/
@SpringBootTest(classes = BeanApp.class)
@RunWith(SpringRunner.class)
public class MyTest {
    @Autowired
    ExecutorService executorService;
    @Test
    public void test1() throws Exception{
        executorService.testExecutor();
        // 延时防止程序立刻执行完，上面的异步还没有执行完的情况
        Thread.sleep(1000);
    }
}
