package com.weil.chat.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName UserLoginImp
 * @Author weil
 * @Description //用户登录实现
 * @Date 2021/9/15 11:05
 * @Version 1.0.0
 **/
public class UserLoginImp implements UserLogin {

    private static final Map<String, String> loginInfo = new ConcurrentHashMap<>();
    static {
        loginInfo.put("zhangsan", "123");
        loginInfo.put("lisi", "123");
        loginInfo.put("wangwu", "123");
        loginInfo.put("zhaoliu", "123");
    }
    @Override
    public boolean login(String name, String pwd) {
        String s = loginInfo.get(name);
        if(s == null)
            return false;
        return s.equals(pwd);
    }
}
