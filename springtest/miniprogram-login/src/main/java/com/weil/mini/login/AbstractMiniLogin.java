package com.weil.mini.login;

import com.weil.mini.config.AuthConfig;
import com.weil.mini.config.AuthUri;
import com.weil.mini.config.AuthUriSource;
import com.weil.mini.exception.MiniLoginException;
import com.weil.mini.model.*;
import com.weil.mini.utils.JwtUtil;
import com.weil.mini.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Name: AbstractMiniLogin
 * @Description: 小程序登录抽象类
 * @Author: weil
 * @Date: 2022-09-02 10:01
 * @Version: 1.0
 */
@Slf4j
public abstract class AbstractMiniLogin implements MiniLogin {
    protected AuthConfig authConfig;
    protected AuthUri authUri;

    public AbstractMiniLogin(AuthConfig authConfig, AuthUri authUri) {
        this.authConfig = authConfig;
        this.authUri = authUri;
        // 该位置可以做参数的校验 TODO
    }

    @Override
    public LoginRes login(AuthParams authParams) {
        try {
            // 1.通过授权码code和开发者信息进行访问授权
            LoginInfo loginInfo = accessAuth(authParams.getAuthCode());
            // 2.获取或者解析前端传入的后端信息；如果后端可以，就自己请求 todo
//        AuthUser authUser = getUserInfo(authParams);

            // 3.本地数据库查询该用户是否存在
            User user = checkLocalUser(loginInfo);
            // 4.生成自定义登录状态jwt token并缓存

            Map<String, Object> tokenMap = generateTokenAndSave(user);
            // 5.end最终返回前端
            return LoginRes.builder().token(tokenMap).build();
        } catch (Exception e) {
            log.error("Failed to login with mini-program", e);
            throw new MiniLoginException("Failed to login with mini-program", e);
        }
    }

    /**
     * 检查本地是否存在该用户，存在则更新信息；不存在则新增
     */
    private User checkLocalUser(LoginInfo loginInfo) {
        return User.builder().openId(loginInfo.getOpenid())
                // 临时 todo
                .id("123")
                .build();
    }

    /**
     * 生成token并缓存
     */
    private Map<String, Object> generateTokenAndSave(User user){
        JwtUtil jwtUtil = SpringContextUtil.getBean(JwtUtil.class);
        return jwtUtil.generateTokenAndSave(user);
    }

    /**
     * 解析或者获取用户基本信息
     */
//    protected abstract AuthUser getUserInfo(AuthParams authParams);

    /**
     * 登录授权，获取用户唯一标识等
     */
    protected abstract LoginInfo accessAuth(String authCode) throws Exception;
}
