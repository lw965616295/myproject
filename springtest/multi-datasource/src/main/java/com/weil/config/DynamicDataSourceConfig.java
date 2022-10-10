package com.weil.config;

import com.weil.common.DynamicConstants;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Name: DynamicDataSourceConfig
 * @Description: 动态数据源配置
 * @Author: weil
 * @Date: 2022-10-10 17:41
 * @Version: 1.0
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DynamicDataSourceConfig {
    /**
     * 主数据源
     */
    @Bean(DynamicConstants.DS_MASTER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDs(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 从数据源
     */
    @Bean(DynamicConstants.DS_SLAVE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDs(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 动态数据源
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource(){
        Map<Object, Object> map = new HashMap<>(2);
        map.put(DynamicConstants.DS_MASTER, masterDs());
        map.put(DynamicConstants.DS_SLAVE, slaveDs());
        // 设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(masterDs());
        return dynamicDataSource;
    }
}
