package com.weil.service;

import com.alibaba.fastjson.JSONObject;
import com.github.lianjiatech.retrofit.spring.boot.annotation.RetrofitClient;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * @InterfaceName RetrofitServer
 * @Author weil
 * @Description //TODO
 * @Date 2022/2/17 11:21
 * @Version 1.0.0
 **/
@RetrofitClient(baseUrl = "${mock.url}", connectTimeoutMs=60000, readTimeoutMs = 60000, writeTimeoutMs=60000)
public interface RetrofitServer {
    @GET("get")
    @Headers({"Content-type:application/json;charset=UTF-8"})
    JSONObject getInfo();
}
