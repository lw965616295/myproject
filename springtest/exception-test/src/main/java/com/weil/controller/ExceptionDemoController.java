package com.weil.controller;

import com.weil.common.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ExceptionDemoController
 * @Author weil
 * @Description //测试自定义异常执行
 * @Date 2022/2/18 16:39
 * @Version 1.0.0
 **/
@RestController
public class ExceptionDemoController {
    //定义两个异常，一个是运行时异常，还有一个是自定义异常；只有自定义异常能正常返回统一的异常信息
    @GetMapping("/test1")
    public void test1(){
        throw new RuntimeException();
    }
    @GetMapping("/test2")
    public void test2(){
        throw new CustomException("自定义异常");
    }
}
