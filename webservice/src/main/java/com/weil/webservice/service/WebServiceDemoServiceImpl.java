/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.webservice.service;

import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @ClassName WebServiceDemoServiceImpl
 * @Author liwei
 * @Description //TODO
 * @Date 16:07 2020/5/12
 * @Version 1.0.0
 **/
@Service
@WebService(serviceName = "WebServiceDemoService",
            targetNamespace = "http://service.webservice.weil.com",
            endpointInterface = "com.weil.webservice.service.WebServiceDemoService"
)
public class WebServiceDemoServiceImpl implements WebServiceDemoService {
    public String hello(String name) {
        return "hello"+name;
    }
}
