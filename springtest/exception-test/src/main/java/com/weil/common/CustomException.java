package com.weil.common;

/**
 * @ClassName CustomException
 * @Author weil
 * @Description //自定义异常
 * @Date 2022/2/18 16:30
 * @Version 1.0.0
 **/
public class CustomException extends RuntimeException {
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
