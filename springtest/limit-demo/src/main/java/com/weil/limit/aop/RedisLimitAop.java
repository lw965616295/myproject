package com.weil.limit.aop;

import com.weil.limit.annotation.RedisLimit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name: RedisLimitAop
 * @Description:
 * @Author: weil
 * @Date: 2022-11-22 20:51
 * @Version: 1.0
 */
@Component
@Aspect
@Slf4j
public class RedisLimitAop {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    // 方式二 使用文件的方式创建lua脚本
//    private DefaultRedisScript<Long> redisScript;
//    @PostConstruct
//    public void init(){
//        redisScript = new DefaultRedisScript<>();
//        redisScript.setResultType(Long.class);
//        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("/lua/rateLimiter.lua")));
//    }


    @Before("@annotation(an)")
    public void before(JoinPoint joinPoint, RedisLimit an) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //拿到RedisLimit注解，如果存在则说明需要限流
        RedisLimit redisLimit = method.getAnnotation(RedisLimit.class);

        if (redisLimit != null) {
            //获取redis的key
            String key = redisLimit.key();
            String className = method.getDeclaringClass().getName();
            String name = method.getName();
            String limitKey = key + className + name;
            log.info(limitKey);

            if (StringUtils.isEmpty(key)) {
                throw new RuntimeException("key cannot be null");
            }

            long limit = redisLimit.permits();
            long expire = redisLimit.expire();

            List<String> keys = new ArrayList<>();
            keys.add(key);
            String luaScript = buildLuaScript();
            // 这里需要泛型Long ，可能是lua脚本返回值是这个类型，换成Integer就报错
            RedisScript<Long> redisScript = new DefaultRedisScript<>(luaScript, Long.class);
            Long count = stringRedisTemplate.execute(redisScript, keys, String.valueOf(limit), String.valueOf(expire));
            log.info("Access try count is {} for key={}", count, key);
            if (count != null && count == 0) {
                log.debug("令牌桶={}，获取令牌失败", key);
                throw new RuntimeException(redisLimit.msg());
            }
        }

    }

    /**
     * 构建redis lua脚本
     *
     * @return
     */
    private String buildLuaScript() {
        StringBuilder luaString = new StringBuilder();
        // 获取三个参数，key limit currentLimit
        luaString.append("local key = KEYS[1]");
        // 获取ARGV内参数Limit
        luaString.append("\n local limit = tonumber(ARGV[1])");
        // 获取当前触发的次数
        luaString.append("\n local currentLimit = tonumber(redis.call('get', key) or \"0\")");
        // 判断当前触发次数+1是否超过限制
        luaString.append("\n if currentLimit + 1 > limit then");
        luaString.append("\n return 0");
        luaString.append("\n else");
        //自增长 1
        luaString.append("\n redis.call(\"INCRBY\", key, 1)");
        //设置过期时间
        luaString.append("\n redis.call(\"EXPIRE\", key, ARGV[2])");
        luaString.append("\n return currentLimit + 1");
        luaString.append("\n end");
        return luaString.toString();
    }

}
