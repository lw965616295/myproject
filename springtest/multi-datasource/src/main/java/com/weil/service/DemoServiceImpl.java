package com.weil.service;

import com.weil.annotation.DS;
import com.weil.common.DynamicConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Name: DemoServiceImpl
 * @Description:
 * @Author: weil
 * @Date: 2022-10-10 20:39
 * @Version: 1.0
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @DS
    public Map<String, Object> getUser() {
        String sql1 = "select * from user where id=?";
        return jdbcTemplate.queryForMap(sql1, "2");
    }

    @Override
    @DS(DynamicConstants.DS_SLAVE)
    public Map<String, Object> getStudent() {
        String sql2 = "select * from student where id=?";
        return jdbcTemplate.queryForMap(sql2, "1");
    }
}
