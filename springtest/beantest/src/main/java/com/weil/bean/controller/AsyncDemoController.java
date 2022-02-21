package com.weil.bean.controller;

import com.weil.bean.service.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName AsyncDemoController
 * @Author weil
 * @Description //测试
 * @Date 2022/2/18 16:13
 * @Version 1.0.0
 **/
@RestController
public class AsyncDemoController {
    @Autowired
    private ExecutorService executorService;
    @GetMapping("/test")
    public String test(){
        executorService.testExecutor();;
        return "test";
    }
}
