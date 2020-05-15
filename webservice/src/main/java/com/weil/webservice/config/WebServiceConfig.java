/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.webservice.config;

import com.weil.webservice.service.StudentService;
import com.weil.webservice.service.WebServiceDemoService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * @ClassName WebServiceConfig
 * @Author liwei
 * @Description //TODO
 * @Date 16:11 2020/5/12
 * @Version 1.0.0
 **/
@Configuration
public class WebServiceConfig {
    @Autowired
    private WebServiceDemoService webServiceDemoService;

    @Autowired
    private StudentService studentService;
    @Autowired
    private Bus bus;
    /**
     * 注入servlet  bean name不能dispatcherServlet 否则会覆盖dispatcherServlet
     * @return
     */
    @Bean(name = "cxfServlet")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(),"/webservice/*");
    }


    /**
     * 注册WebServiceDemoService接口到webservice服务
     * @return
     */
    @Bean
    public Endpoint sweptPayEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, webServiceDemoService);
        endpoint.publish("/webservice");
        return endpoint;
    }
    @Bean
    public Endpoint studentEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, studentService);
        endpoint.publish("/studentService");
        return endpoint;
    }
}
