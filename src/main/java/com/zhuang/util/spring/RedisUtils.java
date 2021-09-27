package com.zhuang.util.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    public static void set(String key, String value, long timeoutSeconds) {
        set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    public static void set(String key, String value, long timeout, TimeUnit timeUnit) {
        _this.stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public static String get(String key) {
        return _this.stringRedisTemplate.opsForValue().get(key);
    }

    public static void delete(String pattern) {
        Set<String> keys = _this.stringRedisTemplate.keys(pattern);
        _this.stringRedisTemplate.delete(keys);
    }

    public Object getNativeConnection(){
        return  _this.stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
    }
}
