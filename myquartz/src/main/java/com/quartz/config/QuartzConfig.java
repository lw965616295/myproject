package com.quartz.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @ClassName QuartzConfig
 * @Author liwei
 * @Description //TODO
 * @Date 17:59 2020/4/8
 * @Version 1.0.0
 **/
@Configuration
public class QuartzConfig {
//    @Autowired
//    private JobFactory jobFactory;

//    @Autowired
//    private AutowireCapableBeanFactory capableBeanFactory;
    @Autowired
    private DataSource dataSource;
    /**
     * 当触发器触发时，与之关联的任务被Scheduler中配置的JobFactory实例化，也就是每触发一次，就会创建一个任务的实例化对象
     * (如果缺省)则调用Job类的newInstance方法生成一个实例
     * (这里选择自定义)并将创建的Job实例化交给IoC管理
     * @return
     */
//    @Bean
//    public JobFactory jobFactory() {
//        return new AdaptableJobFactory() {
//            @Override
//            protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
//                Object jobInstance = super.createJobInstance(bundle);
//                capableBeanFactory.autowireBean(jobInstance);
//                return jobInstance;
//            }
//        };
//    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        schedulerFactoryBean.setJobFactory(jobFactory);
        //延迟启动
        schedulerFactoryBean.setStartupDelay(1);
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        schedulerFactoryBean.setSchedulerName("my_scheduler");
        schedulerFactoryBean.setDataSource(dataSource);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}