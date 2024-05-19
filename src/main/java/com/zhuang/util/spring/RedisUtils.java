package com.zhuang.util.spring;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhuang.util.jackson.JacksonUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    public static void set(String key, Object value) {
        _this.stringRedisTemplate.opsForValue().set(key, objectToString(value));
    }

    public static void set(String key, Object value, int timeoutSeconds) {
        set(key, value, timeoutSeconds, TimeUnit.SECONDS);
    }

    public static void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        _this.stringRedisTemplate.opsForValue().set(key, objectToString(value), timeout, timeUnit);
    }

    public static String get(String key) {
        return _this.stringRedisTemplate.opsForValue().get(key);
    }

    public static <T> T get(String key, Class<T> clazz) {
        String s = get(key);
        if (StrUtil.isEmpty(s)) return null;
        return JacksonUtils.toBean(s, clazz);
    }

    public static List<String> getByPattern(String pattern) {
        Set<String> keys = _this.stringRedisTemplate.keys(pattern);
        return keys.stream().map(RedisUtils::get).collect(Collectors.toList());
    }

    public static List<T> getByPattern(String pattern, Class<T> clazz) {
        List<String> stringList = getByPattern(pattern);
        return stringList.stream().map(c -> JacksonUtils.toBean(c, clazz)).collect(Collectors.toList());
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

    public static String objectToString(Object value) {
        String strValue;
        if (value == null) {
            strValue = null;
        } else if (ClassUtil.isPrimitiveWrapper(value.getClass())) {
            strValue = value.toString();
        } else if (value instanceof Date) {
            strValue = DateUtil.formatDateTime((Date) value);
        } else {
            strValue = JSONUtil.toJsonStr(value);
        }
        return strValue;
    }
}
