package com.weil.tenxun.map;

import com.alibaba.fastjson.JSONObject;

/**
 * @Name: Demo
 * @Description:
 * @Author: weil
 * @Date: 2023-11-01 17:06
 * @Version: 1.0
 */
public class Demo {
    /**
     * 关键词输入提示
     */
    public static void getKeywordInputPrompt(){
        KeywordPromptReq req = new KeywordPromptReq();
        req.setKey(TenxunMapConstants.KEY)
                .setKeyword("日照")
                .setRegion("日照")
                .setLocation("35.425852,119.462392")
                .setPage_size("20");
        String s = TencentMapUtils.getTencentMap(TenxunMapConstants.KEYWORD_INPUT_PROMPT_URL, JSONObject.parseObject(JSONObject.toJSONString(req)));
        System.out.println(s);
    }

    /**
     * 逆地址解析
     */
    public static void getReverseAddress(){
        ReverseAddressReq req = new ReverseAddressReq();
        req.setKey(TenxunMapConstants.KEY)
                .setLocation("35.425852,119.462392");
        String s = TencentMapUtils.getTencentMap(TenxunMapConstants.REVERSE_ADDRESS_RESOLUTION_URL, JSONObject.parseObject(JSONObject.toJSONString(req)));
        System.out.println(s);
    }

    public static void main(String[] args) {
//        getKeywordInputPrompt();
        getReverseAddress();
    }

}
