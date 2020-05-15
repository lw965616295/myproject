package com.quartz.controller;

import com.quartz.entity.Result;
import com.quartz.entity.SchedulerJobEntity;
import com.quartz.service.QuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName QuartzController
 * @Author liwei
 * @Description //TODO
 * @Date 18:04 2020/4/8
 * @Version 1.0.0
 **/
@RestController
public class QuartzController {

    @Autowired
    private QuartzService quartzService;

    /**
     * shennong
     * @catalog kk/aa
     * @title cc
     * @description cc
     * @method get
     * @url https://www.weil.cc/home/insert
     * @param username 必选 string 用户名
     * @param password 必选 string 密码
     * @param pp 必选 string 密码2
     * @param name 可选 string 用户昵称
     * @return {"error_code":0,"data":{"uid":"1","username":"12154545","name":"吴系挂","groupid":2,"reg_time":"1436864169","last_login_time":"0"}}
     * @return_param groupid int 用户组id
     * @return_param name string 用户昵称
     * @remark 这里是备注信息
     * @number 99
     */
    @GetMapping("/insert")
    public String insertTask(String jName, String jGroup, String tName, String tGroup, String cron) {
        quartzService.addJob(jName, jGroup, tName, tGroup, cron);
        return "添加成功！";
    }

    /**
     * shennong
     * @catalog kk/aa
     * @title cc
     * @description jj
     * @method get
     * @url https://www.weil.cc/home/insert44444
     * @param username 必选 string 用户名111
     * @param password 必选 string 密码11
     * @param pp 必选 string 密码2
     * @param name 可选 string 用户昵称
     * @return {"error_code":0,"data":{"uid":"1","username":"12154545","name":"吴系挂","groupid":2,"reg_time":"1436864169","last_login_time":"0"}}
     * @return_param groupid int 用户组id
     * @return_param name string 用户昵称
     * @remark 这里是备注信息
     * @number 99
     */
    @GetMapping("/pause")
    public String pauseTask(String jName, String jGroup) {
        quartzService.pauseJob(jName, jGroup);
        return "暂停成功！";
    }

    /**
     * 继续任务
     */
    @GetMapping("/resume")
    public String resumeTask(String jName, String jGroup) {
        quartzService.resumeJob(jName, jGroup);
        return "继续成功！";
    }

    /**
     * 删除任务
     */
    @GetMapping("/delete")
    public String deleteTask(String jName, String jGroup) {
        quartzService.deleteJob(jName, jGroup);
        return "删除成功！";
    }
    @GetMapping("jobs")
    public Result<List<SchedulerJobEntity>> listAll(){
        return quartzService.listAll();
    }

}

