package com.weil.controller;

import com.weil.common.DynamicConstants;
import com.weil.common.DynamicDataSourceContextHolder;
import com.weil.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Name: DemoController
 * @Description:
 * @Author: weil
 * @Date: 2022-10-10 19:38
 * @Version: 1.0
 */
@RestController
public class DemoController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @GetMapping("/test")
    public void test(){
        String sql1 = "select * from user where id=?";
        Map<String, Object> map1 = jdbcTemplate.queryForMap(sql1, "2");
        System.out.println(map1);
        // 切换数据源，使用slave
        DynamicDataSourceContextHolder.setContext(DynamicConstants.DS_SLAVE);
        String sql2 = "select * from student where id=?";
        Map<String, Object> map2 = jdbcTemplate.queryForMap(sql2, "1");
        System.out.println(map2);
        // 恢复数据源
        DynamicDataSourceContextHolder.removeContext();

        // 以上方式需要在编码中进行设置数据源，代码侵入性大；可以改用aop方式进行改造
    }

    @Autowired
    DemoService demoService;
    @GetMapping("/test2")
    public void test2(){
        System.out.println(demoService.getUser());
        System.out.println(demoService.getStudent());
    }
}
