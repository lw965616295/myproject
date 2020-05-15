/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.webservice.service;

import com.weil.webservice.entity.Student;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @ClassName StudentService
 * @Author liwei
 * @Description //TODO
 * @Date 17:56 2020/5/13
 * @Version 1.0.0
 **/
@WebService
public interface StudentService {
    @WebMethod
    Student getStudent();
}
