///*
// * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
// */
//package com.test.config;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.BeanFactoryAware;
//import org.springframework.context.annotation.ImportSelector;
//import org.springframework.core.type.AnnotationMetadata;
//
///**
// * @ClassName Myselector
// * @Author liwei
// * @Description //TODO
// * @Date 13:40 2020/5/8
// * @Version 1.0.0
// **/
//public class Myselector implements ImportSelector {
//
//    public String[] selectImports(AnnotationMetadata annotationMetadata) {
//        String[] a = new String[1];
//        a[0]= PackScan.class.getName();
//
//        return a;
//    }
//
//}
