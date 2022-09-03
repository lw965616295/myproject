package com.weil.mini.config;

/**
 * @Name: AuthUriSource
 * @Description: 不同平台小程序对应的uri
 * @Author: weil
 * @Date: 2022-08-31 13:55
 * @Version: 1.0
 */
public enum AuthUriSource implements AuthUri {
    /**
     * 微信小程序
     */
    WECHAT {
        @Override
        public String accessUri() {
            return "https://api.weixin.qq.com/sns/jscode2session";
        }

        @Override
        public String userInfo() {
            return "https://api.weixin.qq.com/sns/userinfo";
        }
    },

    /**
     * 支付宝小程序
     */
    ALIPAY {
        @Override
        public String accessUri() {
            return "https://openapi.alipay.com/gateway.do";
        }

        @Override
        public String userInfo() {
            return "https://openapi.alipay.com/gateway.do";
        }
    },

    /**
     * 钉钉小程序
     */
    DINGTALK {
        @Override
        public String accessUri() {
            return null;
        }

        @Override
        public String userInfo() {
            return null;
        }
    }
}
