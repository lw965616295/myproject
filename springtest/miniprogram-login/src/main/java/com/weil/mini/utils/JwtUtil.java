package com.weil.mini.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.weil.mini.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * jwt工具
 * @author: weil
 * @date: 2022/9/2 14:04
 **/
@Component
@Slf4j
public class JwtUtil {
    /**
     * 小程序中用户唯一标识
     */
    private static final String OPEN_ID = "openId";
    /**
     * 系统中用户id
     */
    private static final String USER_ID = "userId";
    /**
     * 缓存中id前缀
     */
    private static final String JWT_CACHE_PRE = "jwt:userId:";
    /**
     * 用户名
     */
//    private static final String USER_NAME = "username";
    /**
     * 系统登录令牌
     */
    private static final String ACCESS_TOKEN = "access_token";
    /**
     * 刷新令牌
     */
    private static final String REFRESH_TOKEN = "refresh_token";
    /**
     * 超时时间
     */
    private static final String EXPIRE = "expire";

    /**
     * JWT 自定义密钥 在配置文件进行配置
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * JWT 过期时间值 这里写死为和小程序时间一致 7200 秒，也就是两个小时
     */
    private static final Long EXPIRE_TIME = 7200L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据用户登陆信息创建 token map并缓存
     * token map中包含 access_token、refresh_token（主要是超时时间是前面的2倍）、expire
     * 注 : 这里的token会被缓存到redis中,用作为二次验证
     * redis里面缓存的时间应该和jwt token的过期时间设置相同
     * @param user 用户信息
     * @return 返回 token
     */
    public Map<String, Object> generateTokenAndSave(User user) {
        // 生成token
        Map<String, Object> tokenMap = buildToken(user);
        // 缓存token
        cacheToken(tokenMap, user.getId());
        return tokenMap;
    }

    /**
     * 构建token map
     */
    private Map<String, Object> buildToken(User user){
        String accessToken = generateToken(user, EXPIRE_TIME);
        String refreshToken = generateToken(user, EXPIRE_TIME<<1);
        HashMap<String, Object> tokenMap = new HashMap<>(3);
        tokenMap.put(ACCESS_TOKEN, accessToken);
        tokenMap.put(REFRESH_TOKEN, refreshToken);
        tokenMap.put(EXPIRE, EXPIRE_TIME.toString());
        return tokenMap;
    }

    /**
     * jwt生成token
     */
    private String generateToken(User user, long expireTime) {
        // 加密算法进行签名得到token
        // 生成签名
        Algorithm algorithm = Algorithm.HMAC256(secret);
        // 生成token
        String token = JWT.create()
                .withClaim(OPEN_ID, user.getOpenId())
                .withClaim(USER_ID, user.getId())
//                .withClaim(USER_NAME, user.getName())
                //JWT 配置过期时间的正确姿势，因为单位是毫秒，所以需要乘1000
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime * 1000))
                .sign(algorithm);
        return token;
    }

    /**
     * 缓存token map
     */
    private void cacheToken(Map<String, Object> tokenMap, String userId){
        stringRedisTemplate.opsForHash().put(JWT_CACHE_PRE + userId, ACCESS_TOKEN, tokenMap.get(ACCESS_TOKEN));
        stringRedisTemplate.opsForHash().put(JWT_CACHE_PRE + userId, REFRESH_TOKEN, tokenMap.get(REFRESH_TOKEN));
        stringRedisTemplate.opsForHash().put(JWT_CACHE_PRE + userId, EXPIRE, tokenMap.get(EXPIRE));
        // 这里使用putAll会出现类型装换异常，可能是需要重新配置序列化，暂时不考虑
//        stringRedisTemplate.opsForHash().putAll(JWT_CACHE_PRE + userId, tokenMap);
        stringRedisTemplate.expire(JWT_CACHE_PRE + userId, EXPIRE_TIME<<1, TimeUnit.SECONDS);
    }




    /**
     * 校验token是否正确
     * 1.根据前端传入的token解密，解密出jwtId，从redis中查找出redisToken，匹配是否相同
     * 2.然后再对redisToken进行解密，解密成功则 继续流程 和 进行token续期
     *
     * @param token 密钥
     * @return 返回是否校验通过
     */
    public boolean verifyToken(String token) {
        try {
            // 1.根据token解密，解密出jwtId , 先从redis中查找出redisToken，匹配是否相同
            String jwtId = getJwtIdByToken(token);
            String redisToken = stringRedisTemplate.opsForHash().get(jwtId, ACCESS_TOKEN).toString();
            if (!redisToken.equals(token)) {
                return false;
            }
            // 2.得到算法相同的JWTVerifier
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String openId = getOpenIdByToken(token);
            String userId = getUserIdByToken(token);
//            String userName = getUserNameByToken(token);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(OPEN_ID, openId)
                    .withClaim(USER_ID, userId)
//                    .withClaim(USER_NAME, userName)
                    //续期
                    .acceptExpiresAt(System.currentTimeMillis() + EXPIRE_TIME * 1000)
                    .build();
            // 3.验证token
            verifier.verify(redisToken);
            // 4.Redis缓存JWT续期
            Map<String, Object> tokenMap = buildToken(User.builder()
                    .openId(openId)
                    .id(userId)
//                    .name(userName)
                    .build());
            stringRedisTemplate.opsForHash().put(JWT_CACHE_PRE + userId, ACCESS_TOKEN, tokenMap.get(ACCESS_TOKEN));
            stringRedisTemplate.opsForHash().put(JWT_CACHE_PRE + userId, REFRESH_TOKEN, tokenMap.get(REFRESH_TOKEN));
            stringRedisTemplate.opsForHash().put(JWT_CACHE_PRE + userId, EXPIRE, tokenMap.get(EXPIRE));
            stringRedisTemplate.expire(jwtId, EXPIRE_TIME<<1, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 根据Token获取userName
     */
//    public String getUserNameByToken(String token)  {
//        return JWT.decode(token).getClaim(USER_NAME).asString();
//    }

    /**
     * 根据Token获取openId
     */
    public String getOpenIdByToken(String token)  {
        return JWT.decode(token).getClaim(OPEN_ID).asString();
    }

    /**
     * 根据Token 获取jwtId
     */
    public String getJwtIdByToken(String token)  {
        return JWT_CACHE_PRE + getUserIdByToken(token);
    }
    /**
     * 根据Token 获取userId
     */
    public String getUserIdByToken(String token)  {
        return JWT.decode(token).getClaim(USER_ID).asString();
    }

}
