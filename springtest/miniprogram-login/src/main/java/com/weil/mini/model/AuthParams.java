package com.weil.mini.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Name: AuthParams
 * @Description: 访问参数
 * @Author: weil
 * @Date: 2022-08-31 14:34
 * @Version: 1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthParams {
    /**
     * 授权码
     */
    private String authCode;
    /**
     * 开发者id
     */
    private String appId;
    /**
     * 开发者秘钥
     */
    private String appSecret;

    // 微信使用，用于解密用户信息
    // 前端传入的加密信息
    private String encryptedData;
    private String iv;


}
