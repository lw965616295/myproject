/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.webservice.client;

import com.weil.webservice.entity.Student;
import com.weil.webservice.jj.ArrayOfString;
import com.weil.webservice.jj.WeatherWebService;
import com.weil.webservice.jj.WeatherWebServiceHttpPost;
import com.weil.webservice.jj.WeatherWebServiceSoap;
import com.weil.webservice.service.StudentService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;

import javax.xml.namespace.QName;

/**
 * @ClassName Test
 * @Author liwei
 * @Description //TODO
 * @Date 17:00 2020/5/12
 * @Version 1.0.0
 **/
public class Test {
    public static void main(String[] args) throws Exception{
//        test1();
//        test2();
//        test3();
//        test4();
        test5();
    }
    public static void test1(){
        WebServiceDemoService_Service service = new WebServiceDemoService_Service();
        WebServiceDemoService myservice = service.getWebServiceDemoServiceImplPort();
        String world = myservice.hello("world");
        System.out.println(world);
    }
    //动态调用
    public static void test2() throws Exception{
        JaxWsDynamicClientFactory jaxWsDynamicClientFactory = JaxWsDynamicClientFactory.newInstance();
//        String wsdlUrl = "http://localhost:8080/webservice/webservice?wsdl";
//        String wsdlUrl = "http://localhost:8080/webservice/studentService?wsdl";
        String wsdlUrl = "http://ws.webxml.com.cn/WebServices/WeatherWS.asmx?wsdl";
//        String method = "hello";
//        String method = "getStudent";
        String method = "getStudent";
        Client client = jaxWsDynamicClientFactory.createClient(wsdlUrl);

        Endpoint endpoint = client.getEndpoint();
        String namespaceURI = endpoint.getService().getName().getNamespaceURI();
        QName opName = new QName(namespaceURI, method);
        BindingInfo bindingInfo = endpoint.getEndpointInfo().getBinding();
        System.out.println(client);
        if (bindingInfo.getOperation(opName) == null) {
            for (BindingOperationInfo operationInfo : bindingInfo.getOperations()) {
                if (method.equals(operationInfo.getName().getLocalPart())) {
                    opName = operationInfo.getName();
                    break;
                }
            }
        }

        Object[] invoke = client.invoke(opName,"world");
        System.out.println(invoke[0].toString());
    }
    //通过接口协议获取数据类型
    public static void test3() throws Exception{
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress("http://localhost:8080/webservice/webservice?wsdl");
        jaxWsProxyFactoryBean.setServiceClass(WebServiceDemoService.class);
        WebServiceDemoService service = (WebServiceDemoService) jaxWsProxyFactoryBean.create();
        String world = service.hello("world");
        System.out.println(world);
    }
    public static void test4() throws Exception{
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
        jaxWsProxyFactoryBean.setAddress("http://localhost:8080/webservice/studentService?wsdl");
        jaxWsProxyFactoryBean.setServiceClass(StudentService.class);
        StudentService service = (StudentService) jaxWsProxyFactoryBean.create();
        Student student = service.getStudent();
        System.out.println(student);
    }
    public static void test5(){
        WeatherWebService weatherWebService = new WeatherWebService();
        WeatherWebServiceSoap weatherWebServiceSoap = weatherWebService.getWeatherWebServiceSoap12();
        ArrayOfString supportProvince = weatherWebServiceSoap.getSupportProvince();
        System.out.println(supportProvince.getString());

    }
    public static void test6(){
        WeatherWebService weatherWebService = new WeatherWebService();
        WeatherWebServiceHttpPost weatherWebServiceHttpPost = weatherWebService.getWeatherWebServiceHttpPost();
        ArrayOfString supportProvince = weatherWebServiceHttpPost.getSupportProvince();
        System.out.println(supportProvince.getString());

    }
}
