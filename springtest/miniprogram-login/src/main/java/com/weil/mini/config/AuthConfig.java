package com.weil.mini.config;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Name: AuthConfig
 * @Description: 小程序需要的配置信息
 * @Author: weil
 * @Date: 2022-09-02 10:05
 * @Version: 1.0
 */
@Data
@Builder
@Component
public class AuthConfig {
    // 微信配置信息
    /**
     * 开发者id
     */
    @Value("${mini.wechat.appid}")
    private String wechatAppid;
    /**
     * 秘钥
     */
    @Value("${mini.wechat.secret}")
    private String secret;

    // 支付宝配置信息
    @Value("${mini.alipay.appid}")
    private String alipayAppid;
    @Value("${mini.alipay.private_key}")
    private String privateKey;
    @Value("${mini.alipay.public_key}")
    private String publicKey;
}
