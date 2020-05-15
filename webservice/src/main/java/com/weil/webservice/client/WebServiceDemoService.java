
package com.weil.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "WebServiceDemoService", targetNamespace = "http://service.webservice.weil.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface WebServiceDemoService {


    /**
     * 
     * @param name
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "hello", targetNamespace = "http://service.webservice.weil.com/", className = "com.weil.webservice.client.Hello")
    @ResponseWrapper(localName = "helloResponse", targetNamespace = "http://service.webservice.weil.com/", className = "com.weil.webservice.client.HelloResponse")
    public String hello(
        @WebParam(name = "name", targetNamespace = "")
        String name);

}
