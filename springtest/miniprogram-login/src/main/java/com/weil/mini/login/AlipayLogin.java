package com.weil.mini.login;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.weil.mini.config.AuthConfig;
import com.weil.mini.config.AuthUri;
import com.weil.mini.config.AuthUriSource;
import com.weil.mini.exception.MiniLoginException;
import com.weil.mini.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Name: AlipayLogin
 * @Description: 支付宝登录实现
 * @Author: weil
 * @Date: 2022-09-03 10:37
 * @Version: 1.0
 */
@Slf4j
public class AlipayLogin extends AbstractMiniLogin {

    AlipayClient alipayClient;

    public AlipayLogin(AuthConfig authConfig) {
        this(authConfig, AuthUriSource.ALIPAY);
        alipayClient = new DefaultAlipayClient(AuthUriSource.ALIPAY.accessUri(), authConfig.getAlipayAppid(), authConfig.getPrivateKey(),"json","GBK", authConfig.getPublicKey(),"RSA2");
    }

    public AlipayLogin(AuthConfig authConfig, AuthUri authUri) {
        super(authConfig, authUri);
    }

    @Override
    protected LoginInfo accessAuth(String authCode) throws Exception {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(authCode);
//        request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
        AlipaySystemOauthTokenResponse response = null;
        response = alipayClient.execute(request);
        log.info("{},{}", response.getUserId(),response.getAccessToken());
        if(response.isSuccess()){
            return LoginInfo.builder().openid(response.getUserId()).accessToken(response.getAccessToken()).build();
        } else {
            throw new MiniLoginException("调用失败，"+response.getMsg());
        }
    }
}
