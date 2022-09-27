package com.weil;

import com.weil.example.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Name: App
 * @Description:
 * @Author: weil
 * @Date: 2022-09-26 19:46
 * @Version: 1.0
 */
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
        System.out.println("context 启动成功！");
        // 取出bean
        MessageService messageService = context.getBean(MessageService.class);
        // 输出内容
        System.out.println(messageService.getMessage());
    }
}
