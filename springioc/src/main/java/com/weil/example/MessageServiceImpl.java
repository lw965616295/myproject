package com.weil.example;

/**
 * @Name: MessageServiceImpl
 * @Description:
 * @Author: weil
 * @Date: 2022-09-26 19:46
 * @Version: 1.0
 */
public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "hello world";
    }
}
