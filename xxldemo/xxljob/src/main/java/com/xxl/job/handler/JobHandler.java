package com.xxl.job.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName JobHandler
 * @Author liwei
 * @Description //TODO
 * @Date 11:17 2020/4/20
 * @Version 1.0.0
 **/
@Component
@Slf4j
public class JobHandler {

    /**
     * 1、简单任务示例（Bean模式）
     */
    @XxlJob("demo")
    ReturnT<String> demo(String param) throws Exception {
        int a = Integer.parseInt(param);
        XxlJobLogger.log("a{}","22");
        System.out.println(param);
        for (int i = 0; i < 5; i++) {
            XxlJobLogger.log("beat at:" + i);
            TimeUnit.SECONDS.sleep(2);
        }
        return ReturnT.SUCCESS;
    }
    /**
     * 初始，销毁，90s线程自动销毁
     */
    @XxlJob(value = "demo1", init = "init", destroy = "destroy")
    public ReturnT<String> demo1(String param){
        System.out.println("demo1");
        return ReturnT.SUCCESS;
    }
    public void init(){
        System.out.println(">>>>init");
    }
    public void destroy(){
        System.out.println(">>>>destroy");
    }
    /**
     * 分片，协同处理任务
     */
    @XxlJob("shardingJobHandler")
    public ReturnT<String> shardingJobHandler(String param) throws Exception {

        // 分片参数
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        log.info("分片参数：当前分片序号 = {}, 总分片数 = {}", shardingVO.getIndex(), shardingVO.getTotal());

        // 业务逻辑
        for (int i = 0; i < shardingVO.getTotal(); i++) {
            if (i == shardingVO.getIndex()) {
                log.info("第 {} 片, 命中分片开始处理", i);
            } else {
                log.info("第 {} 片, 忽略", i);
            }
        }

        return ReturnT.SUCCESS;
    }
    /**
     * 阻塞测试
     */
    @XxlJob("demo2")
    public ReturnT<Object> demo2(String param) throws InterruptedException {
        System.out.println(">>>>>>开始");
        for(int a=1; a<10; a++){
            System.out.println(a);
            TimeUnit.SECONDS.sleep(2l);
        }
        System.out.println(">>>>>>结束");
        return new ReturnT<Object>(200,"成功");
    }
}
