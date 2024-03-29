package com.weil.bean.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

//import javax.sql.DataSource;
//
///**
// * @ClassName DataSourceConfig
// * @Author weil
// * @Description //多数据源
// * @Date 2022/2/22 17:26
// * @Version 1.0.0
// **/
//@Configuration
//public class DataSourceConfig {
//    //主数据源配置 ds1数据源
//    @Primary
//    @Bean(name = "ds1DataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.ds1")
//    public DataSourceProperties ds1DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    //主数据源 ds1数据源
//    @Primary
//    @Bean(name = "ds1DataSource")
//    public DataSource ds1DataSource(@Qualifier("ds1DataSourceProperties") DataSourceProperties dataSourceProperties) {
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }
//
//    //第二个ds2数据源配置
//    @Bean(name = "ds2DataSourceProperties")
//    @ConfigurationProperties(prefix = "spring.datasource.ds2")
//    public DataSourceProperties ds2DataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    //第二个ds2数据源
//    @Bean("ds2DataSource")
//    public DataSource ds2DataSource(@Qualifier("ds2DataSourceProperties") DataSourceProperties dataSourceProperties) {
//        return dataSourceProperties.initializeDataSourceBuilder().build();
//    }
//}
