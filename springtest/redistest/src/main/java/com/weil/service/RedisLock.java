package com.weil.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

/**
 * @ClassName RedisLock
 * @Author weil
 * @Description //redis分布式锁
 * @Date 2022/2/8 11:00
 * @Version 1.0.0
 **/
@Service
@Slf4j
public class RedisLock {
    // 锁键key
    private String lock_key = "redis_lock";
    // 锁过期时间30s
    private Long lock_timeout = 30000L;
    // 获取锁超时时间100s
    private Long timeout = 100000L;
    // 解锁LUA脚本，原子性
    private String script = "if redis.call('get',KEYS[1]) == ARGV[1] then " +
            "   return redis.call('del',KEYS[1]) " +
            "   else" +
            "   return 0 " +
            "   end";

    JedisPool jedisPool = new JedisPool();
    // set参数
    SetParams params = SetParams.setParams().nx().px(lock_timeout);

    /**
     * 加锁
     * id 唯一value
     */
    public boolean lock(String id){
        Jedis jedis = jedisPool.getResource();
        long start = System.currentTimeMillis();
        try {
            while (true){
                // 循环获取锁
                String lock = jedis.set(lock_key, id, params);
                if("OK".equals(lock)){
                    log.info("id:{}，加锁成功", id);
                    //获取成功
                    return true;
                }else {
                    log.info("id:{}，加锁失败", id);
                    // 获取失败
                    long usedTime = System.currentTimeMillis() - start;
                    if(usedTime > timeout){
                        log.info("id:{}，加锁超时", id);
                        // 获取锁超时了
                        return false;
                    }
                    // 延时下，防止cpu使用率太高
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }finally {
            jedis.close();
        }
    }

    /**
     * 解锁
     */
    public boolean unlock(String id){
        Jedis jedis = jedisPool.getResource();
        try {
            Object res = jedis.eval(script, Collections.singletonList(lock_key), Collections.singletonList(id));
            if("1".equals(res.toString())){
                log.info("id:{}，解锁成功", id);
                // 解锁成功
                return true;
            }
            log.info("id:{}，解锁失败", id);
            return false;
        }finally {
            jedis.close();
        }
    }
}
