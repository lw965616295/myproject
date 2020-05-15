/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */
package com.weil.controller;

import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

/**
 * @ClassName DemoController
 * @Author liwei
 * @Description //TODO
 * @Date 16:03 2020/4/30
 * @Version 1.0.0
 **/
public class DemoController {
    public static void main(String[] args) {
        KieHelper kieHelper = new KieHelper();
        String drl="package com.weil.controller;\n" +
                "dialect  \"mvel\"\n" +
                "\n" +
                "rule \"hello\"\n" +
                "    when\n" +
                "    str: String(str.equals(\"hello\"))\n" +
                "    then\n" +
                "       System.out.println(\"hello world!\");\n" +
                "end";
        kieHelper.addContent(drl, ResourceType.DRL);
        KieSession kieSession = kieHelper.build().newKieSession();
        kieSession.insert("hello");
        int i = kieSession.fireAllRules();
        System.out.println("次数" + i);
        kieSession.dispose();
    }
}
