package com.weil.mini.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Name: AuthToken
 * @Description: 授权码code换取的信息
 * @Author: weil
 * @Date: 2022-08-31 14:23
 * @Version: 1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfo implements Serializable {

    private String openid;
    private String sessionKey;

    private String unionid;
    private String errcode = "0";
    private String errmsg;


    private String accessToken;
    private String userId;
}
