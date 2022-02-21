package com.weil.common;

import com.weil.controller.ExceptionDemoController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName CustomExceptionHandler
 * @Author weil
 * @Description //自定义异常处理
 * @Date 2022/2/18 16:35
 * @Version 1.0.0
 **/
// assignableTypes指定对哪个controller进行处理
@RestControllerAdvice(assignableTypes = {ExceptionDemoController.class})
public class CustomExceptionHandler {
    // 拦截自定异常
    @ExceptionHandler(CustomException.class)
    public ReturnEntity<CustomExceptionResp> handler(Exception e){
        CustomExceptionResp resp = new CustomExceptionResp("5000", e.getMessage(), e.getClass().getName());
        return new ReturnEntity<CustomExceptionResp>().setT(resp);
    }
}
