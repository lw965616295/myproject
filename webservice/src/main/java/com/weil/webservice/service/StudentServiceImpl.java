/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.webservice.service;

import com.weil.webservice.entity.Student;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @ClassName StudentServiceImpl
 * @Author liwei
 * @Description //TODO
 * @Date 19:23 2020/5/13
 * @Version 1.0.0
 **/
@Service
@WebService(serviceName = "StudentService",
            targetNamespace = "http://service.webservice.weil.com",
            endpointInterface = "com.weil.webservice.service.StudentService")
public class StudentServiceImpl implements StudentService {
    public Student getStudent() {
        Student student = new Student();
        student.setName("weil");
        student.setAge(12);
        return student;
    }
}
