/*
 * Copyright (c) 2019. Jiangsu Sesan Technology Co., Ltd All rights reserved.
 */

package com.quartz.entity;

import lombok.Data;

import java.util.Map;

/**
 * @ClassName SchedulerEntity
 * @Author Tangshun
 * @Description 定时任务的对象
 * @Date 21:12 2019/6/21
 * @Version 1.0.0
 **/
@Data
public class SchedulerJobEntity {
    //触发器名称
    private String triggerName;
    //触发器归属组
    private String triggerGroup;
    //调度cron
    private String schedulerCron;
    //工作名称
    private String jobName;
    //工作归属组
    private String jobGroup;
    //工作详细 包括源码 key为src value为src的内容
    private Map<String,String> jobDataMap;
    //下次执行时间
    private long nextFireDate;
    //前次执行时间
    private long preFireDate;
    //当前状态
    private String status;

}
