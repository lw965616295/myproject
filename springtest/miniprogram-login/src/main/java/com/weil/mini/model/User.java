package com.weil.mini.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

/**
 * @Name: User
 * @Description: 系统用户类
 * @Author: weil
 * @Date: 2022-09-02 14:34
 * @Version: 1.0
 */
@Data
@Builder
public class User {
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
     * 小程序唯一标识
     */
    private String openId;

    /**
     * 用户授权的信息
     */
    private LoginInfo loginInfo;
    /**
     * 第三方平台返回的原始用户信息
     */
    private JSONObject rawUserInfo;



}
