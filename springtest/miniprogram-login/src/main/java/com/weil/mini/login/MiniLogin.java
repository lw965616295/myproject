package com.weil.mini.login;

import com.weil.mini.exception.MiniLoginException;
import com.weil.mini.model.AuthParams;
import com.weil.mini.model.LoginRes;

/**
 * @Name: MiniLogin
 * @Description: 小程序登录接口
 * @Author: weil
 * @Date: 2022-09-02 09:38
 * @Version: 1.0
 */
public interface MiniLogin {
    default LoginRes login(AuthParams authParams){
        throw new MiniLoginException("not implement");
    }
}
