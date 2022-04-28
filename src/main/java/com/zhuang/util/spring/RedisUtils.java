package com.zhuang.util.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    private static RedisUtils _this;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    private void init() {
        _this = this;
    }

    public static void publish(String channel, Object message) {
        _this.stringRedisTemplate.convertAndSend(channel, message);
    }

    public static void set(String key, String value) {
        _this.stringRedisTemplate.opsForValue().set(key, value);
    }

    public static void set(String key, String value, int timeoutSeconds) {
        set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    public static void set(String key, String value, long timeout, TimeUnit timeUnit) {
        _this.stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public static String get(String key) {
        return _this.stringRedisTemplate.opsForValue().get(key);
    }

    public static void delete(String key) {
        _this.stringRedisTemplate.delete(key);
    }

    public static void delete(Collection<String> keys) {
        _this.stringRedisTemplate.delete(keys);
    }

    public static void deleteByPattern(String pattern) {
        Set<String> keys = _this.stringRedisTemplate.keys(pattern);
        _this.stringRedisTemplate.delete(keys);
    }

    public static Long getExpire(String key, TimeUnit timeUnit) {
        return _this.stringRedisTemplate.getExpire(key, timeUnit);
    }

    public static Long getExpire(String key) {
        return _this.stringRedisTemplate.getExpire(key);
    }

    public static Object getNativeConnection() {
        return _this.stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
    }
}
