package com.weil.mini.model;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Name: AuthUser
 * @Description: 用户的信息
 * @Author: weil
 * @Date: 2022-08-31 14:25
 * @Version: 1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 用户网址
     */
    private String blog;
    /**
     * 所在公司
     */
    private String company;
    /**
     * 位置
     */
    private String location;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户备注（各平台中的用户个人介绍）
     */
    private String remark;
    /**
     * 用户来源
     */
    private String source;
    /**
     * 用户授权的token信息
     */
    private LoginInfo loginInfo;
    /**
     * 第三方平台返回的原始用户信息
     */
    private JSONObject rawUserInfo;
}
