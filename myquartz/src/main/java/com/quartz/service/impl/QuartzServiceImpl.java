package com.quartz.service.impl;

import com.quartz.entity.Result;
import com.quartz.entity.SchedulerJobEntity;
import com.quartz.jobs.HelloJob;
import com.quartz.service.QuartzService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName QuartzServiceImpl
 * @Author liwei
 * @Description //TODO
 * @Date 18:02 2020/4/8
 * @Version 1.0.0
 **/
@Service
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private Scheduler scheduler;

    /**
     * 新增一个定时任务
     * @param jName 任务名称
     * @param jGroup 任务组
     * @param tName 触发器名称
     * @param tGroup 触发器组
     * @param cron cron表达式
     */
    public void addJob(String jName, String jGroup, String tName, String tGroup, String cron) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                    .withIdentity(jName, jGroup)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(tName, tGroup)
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 暂停定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    public void pauseJob(String jName, String jGroup) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jName, jGroup));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 继续定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    public void resumeJob(String jName, String jGroup) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jName, jGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除定时任务
     * @param jName 任务名
     * @param jGroup 任务组
     */
    public void deleteJob(String jName, String jGroup) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jName, jGroup));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Result<List<SchedulerJobEntity>> listAll() {
        List<SchedulerJobEntity> schedulerJobEntities=new ArrayList<>();
        try {
            SchedulerJobEntity schedulerJobEntity=null;
            //获取所有jobGroup名
            List<String> jobGroupNames = scheduler.getJobGroupNames();
            for (String groupName : jobGroupNames) {
                //获取同一组的所有jobKey
                Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName));
                for (JobKey jobKey : jobKeys) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    //封装数据集
                    Map<String,String> dataMap=new HashMap<>();
                    String[] keys = jobDetail.getJobDataMap().getKeys();
                    for(String key:keys){
                        dataMap.put(key,jobDetail.getJobDataMap().getString(key));
                    }
                    //获取触发器
                    List<? extends Trigger> triggersOfJob = scheduler.getTriggersOfJob(jobKey);
                    Trigger trigger = triggersOfJob.get(0);
                    schedulerJobEntity=new SchedulerJobEntity();
                    schedulerJobEntity.setJobName(jobKey.getName());
                    schedulerJobEntity.setJobGroup(jobKey.getGroup());
                    schedulerJobEntity.setJobDataMap(dataMap);
                    schedulerJobEntity.setTriggerName(trigger.getKey().getName());
                    schedulerJobEntity.setTriggerGroup(trigger.getKey().getGroup());
                    schedulerJobEntity.setNextFireDate(trigger.getNextFireTime().getTime());
                    schedulerJobEntity.setPreFireDate(trigger.getPreviousFireTime().getTime());
                    String k=trigger.getKey().getGroup()+":"+trigger.getKey().getName()+","+jobKey.getGroup()+":"+jobKey.getName();
                    System.out.println(k);
                    schedulerJobEntity.setStatus(scheduler.getTriggerState(trigger.getKey()).name());
                    if(trigger instanceof CronTrigger){
                        CronTrigger cronTrigger=(CronTrigger)trigger;
                        schedulerJobEntity.setSchedulerCron(cronTrigger.getCronExpression());
                    }
                    schedulerJobEntities.add(schedulerJobEntity);
                }
            }
        } catch (Exception e) {
            return Result.failed("获取调度列表失败"+e.getMessage());
        }

        return Result.succeed(schedulerJobEntities);
    }

}
