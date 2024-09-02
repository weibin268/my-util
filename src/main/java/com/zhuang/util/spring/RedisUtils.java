package com.zhuang.util.spring;

import cn.hutool.core.util.StrUtil;
import com.zhuang.util.jackson.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
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

    public static void put(String key, String hashKey, String value) {
        _this.stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }

    public static void expireForHash(String key, long timeout, TimeUnit unit) {
        _this.stringRedisTemplate.opsForHash().getOperations().expire(key, timeout, unit);
    }

    public static void expireForValue(String key, long timeout, TimeUnit unit) {
        _this.stringRedisTemplate.opsForValue().getOperations().expire(key, timeout, unit);
    }

    public static String get(String key) {
        return _this.stringRedisTemplate.opsForValue().get(key);
    }

    public static String get(String key, String hashKey) {
        Object o = _this.stringRedisTemplate.opsForHash().get(key, hashKey);
        return o == null ? null : o.toString();
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

    public static List<String> getByPattern(String pattern) {
        Set<String> keys = _this.stringRedisTemplate.keys(pattern);
        return keys.stream().map(RedisUtils::get).collect(Collectors.toList());
    }

    public static <T> List<T> getByPattern(String pattern, Class<T> clazz) {
        List<String> stringList = getByPattern(pattern);
        return stringList.stream().filter(c -> StrUtil.isNotEmpty(c)).map(c -> {
            T t = null;
            try {
                t = JacksonUtils.toBean(c, clazz);
            } catch (Exception ex) {
                log.error("RedisUtils.getByPattern error!", ex);
            }
            return t;
        }).filter(c -> c != null).collect(Collectors.toList());
    }
}
