package com.weil.mini.login;

import com.alibaba.fastjson.JSONObject;
import com.weil.mini.config.AuthConfig;
import com.weil.mini.config.AuthUri;
import com.weil.mini.config.AuthUriSource;
import com.weil.mini.config.RestTemplateConfig;
import com.weil.mini.model.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @Name: WechatLogin
 * @Description:
 * @Author: weil
 * @Date: 2022-09-02 10:46
 * @Version: 1.0
 */
@Slf4j
public class WechatLogin extends AbstractMiniLogin {

    public WechatLogin(AuthConfig authConfig) {
        this(authConfig, AuthUriSource.WECHAT);
    }

    public WechatLogin(AuthConfig authConfig, AuthUri authUri){
        super(authConfig, authUri);
    }

    @Override
    protected LoginInfo accessAuth(String authCode) throws Exception {
        String code2SessionUrl = authUri.accessUri();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", authConfig.getWechatAppid());
        params.add("secret", authConfig.getSecret());
        params.add("js_code", authCode);
        params.add("grant_type", "authorization_code");
        URI code2Session = getURIwithParams(code2SessionUrl, params);

        String s = RestTemplateConfig.getRestTemplate().exchange(code2Session, HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class).getBody();
        log.info(s);
        // 解析
        LoginInfo authLoginInfo = JSONObject.parseObject(s, LoginInfo.class);
        if(!authLoginInfo.getErrcode().equals("0")){
            throw new RuntimeException(authLoginInfo.getErrmsg());
        }
        return authLoginInfo;
    }
    /**
     * URI工具类
     *
     * @param url    url
     * @param params 参数
     * @return URI
     */
    private URI getURIwithParams(String url, MultiValueMap<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);
        return builder.build().encode().toUri();
    }
}
