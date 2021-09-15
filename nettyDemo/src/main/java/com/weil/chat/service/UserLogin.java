package com.weil.chat.service;

/**
 * @InterfaceName UserLogin
 * @Author weil
 * @Description //用户登录接口
 * @Date 2021/9/15 11:04
 * @Version 1.0.0
 **/
public interface UserLogin {
    boolean login(String name, String pwd);
}
