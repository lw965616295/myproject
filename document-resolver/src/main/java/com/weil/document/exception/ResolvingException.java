package com.weil.document.exception;

/**
 * @Name: ResolvingException
 * @Description: 文档解析异常
 * @Author: weil
 * @Date: 2024-07-30 09:15
 * @Version: 1.0
 */
public class ResolvingException extends RuntimeException {
    public ResolvingException() {}
    public ResolvingException(String message) {
        super(message);
    }
    public ResolvingException(Throwable cause) {
        super(cause);
    }
}
