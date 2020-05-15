/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.webservice.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @ClassName WebServiceDemoService
 * @Author liwei
 * @Description //TODO
 * @Date 16:05 2020/5/12
 * @Version 1.0.0
 **/
@WebService
public interface WebServiceDemoService {
    @WebMethod
    String hello(@WebParam(name = "name") String name);
}
