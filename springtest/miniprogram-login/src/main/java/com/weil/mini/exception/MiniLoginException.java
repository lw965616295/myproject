package com.weil.mini.exception;

/**
 * @Name: MiniLoginException
 * @Description: 小程序登录自定义异常
 * @Author: weil
 * @Date: 2022-09-02 09:43
 * @Version: 1.0
 */
public class MiniLoginException extends RuntimeException {
    public MiniLoginException() {
    }

    public MiniLoginException(String message) {
        super(message);
    }

    public MiniLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
