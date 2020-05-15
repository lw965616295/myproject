/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */

package com.quartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @ClassName QuartzJob
 * @Author Tangshun
 * @Description //TODO
 * @Date 16:23 2019/6/21
 * @Version 1.0.0
 **/
//持久化
@PersistJobDataAfterExecution
//禁止并发执行(Quartz不要并发地执行同一个job定义（这里指一个job类的多个实例）)
@DisallowConcurrentExecution
@Slf4j
public class QuartzJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String[] keys = jobDataMap.getKeys();
        for(String s:keys){
            System.out.println(s+"===="+jobDataMap.get(s));
        }
        String taskName = (String)jobDataMap.get("name");
        log.info("---> Quartz job {}, {} <----", new Date(), taskName);
    }

}
