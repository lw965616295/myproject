package com.quartz.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ClassName HelloJob
 * @Author liwei
 * @Description //TODO
 * @Date 18:00 2020/4/8
 * @Version 1.0.0
 **/
public class HelloJob extends QuartzJobBean {
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.err.println("hello world");
    }
}
