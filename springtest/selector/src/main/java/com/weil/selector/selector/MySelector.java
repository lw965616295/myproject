/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.selector.selector;

import com.weil.selector.annotation.EnableConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MySelector
 * @Author liwei
 * @Description //TODO
 * @Date 9:37 2020/5/22
 * @Version 1.0.0
 **/
public class MySelector implements ImportSelector {
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        boolean contains = annotationMetadata.getAnnotationTypes().contains(EnableConfig.class.getName());
        List<String> list = new ArrayList<String>();
        list.add("aa");
        list.add("bb");
        String[] strings = list.toArray(new String[0]);
        return new String[0];

    }
}
