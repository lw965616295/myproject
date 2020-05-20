/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.test.controller;

import com.test.entity.Student;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName DemoController
 * @Author liwei
 * @Description //TODO
 * @Date 15:27 2020/5/8
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping(value = "/ha")
    public String ha(@RequestParam int a){
        return "ok";
    }

    public static void main(String[] args) {
//        Boolean a = true;
//        System.out.println(a==true);
//        System.out.println(exceptionTest());

    }
    @RequestMapping("/demo1")
    public String demo1(Date date){
        System.out.println(date);
        return "ok";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/student")
    public String getStudent(@RequestBody Student student){
        System.out.println(student);
        return student.toString();
    }
    public static String exceptionTest(){
        try{
            System.out.println("---->11");
//            int a = 1/0;
//            return "11";
        }catch (Exception e){
            System.out.println("----->22");
        }finally {
            System.out.println("---->33");
        }
        return "44";
    }
}
