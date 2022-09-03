package com.weil.mini.config;

/**
 * @Name: AuthUri
 * @Description: 小程序访问接口uri
 * @Author: weil
 * @Date: 2022-08-31 13:45
 * @Version: 1.0
 */
public interface AuthUri {
    /**
     * 接收后端传入的code等授权信息的uri
     */
    String accessUri();

    /**
     * 获取用户信息的uri
     */
    String userInfo();
}
