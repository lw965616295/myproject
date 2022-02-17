package com.weil.controller;

import com.alibaba.fastjson.JSONObject;
import com.weil.service.RetrofitServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RetrofitController
 * @Author weil
 * @Description //controller
 * @Date 2022/2/17 11:29
 * @Version 1.0.0
 **/
@RestController
public class RetrofitController {
    @Autowired
    private RetrofitServer retrofitServer;
    @GetMapping("hello")
    public JSONObject getInfo(){
        JSONObject str = retrofitServer.getInfo();
        System.out.println(str);
        return str;
    }
}
