package com.weil.mini.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Name: LoginRes
 * @Description: 登录响应
 * @Author: weil
 * @Date: 2022-09-02 09:40
 * @Version: 1.0
 */
@Data
@Builder
public class LoginRes {
    /**
     * 服务器token
     */
    private Object token;
}
