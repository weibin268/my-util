package com.zhuang.util.spring;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zhuang.util.jackson.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
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
        return stringToObject(get(key), clazz);
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

    public static void expire(String key, long timeout, TimeUnit unit) {
        _this.stringRedisTemplate.expire(key, timeout, unit);
    }

    public static Object getNativeConnection() {
        return _this.stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
    }

    public static List<String> getListByPattern(String pattern) {
        Set<String> keys = _this.stringRedisTemplate.keys(pattern);
        return keys.stream().map(RedisUtils::get).collect(Collectors.toList());
    }

    public static <T> List<T> getListByPattern(String pattern, Class<T> clazz) {
        List<String> stringList = getListByPattern(pattern);
        return stringList.stream().filter(StrUtil::isNotEmpty).map(c -> {
            T t = null;
            try {
                t = JacksonUtils.toBean(c, clazz);
            } catch (Exception ex) {
                log.error("RedisUtils.getListByPattern error!", ex);
            }
            return t;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }


    public static HashOperations<String, String, String> opsForHash() {
        return _this.stringRedisTemplate.opsForHash();
    }

    public static String getForHash(String key, String hashKey) {
        return opsForHash().get(key, hashKey);
    }

    public static <T> T getForHash(String key, String hashKey, Class<T> clazz) {
        return stringToObject(getForHash(key, hashKey), clazz);
    }

    public static <T> List<T> getListForHash(String key, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        Map<String, String> stringStringMap = entriesForHash(key);
        Collection<String> values = stringStringMap.values();
        for (String value : values) {
            result.add(stringToObject(value, clazz));
        }
        return result;
    }

    public static void putForHash(String key, String hashKey, Object value) {
        opsForHash().put(key, hashKey, objectToString(value));
    }

    public static void putAllForHash(String key, Map<String, String> map) {
        opsForHash().putAll(key, map);
    }

    public static Set<String> keysForHash(String key) {
        return opsForHash().keys(key);
    }

    public static Map<String, String> entriesForHash(String key) {
        return opsForHash().entries(key);
    }

    public static void deleteForHash(String key, Object... hashKeys) {
        opsForHash().delete(key, hashKeys);
    }

    public static void expireForHash(String key, long timeout, TimeUnit unit) {
        opsForHash().getOperations().expire(key, timeout, unit);
    }

    private static String objectToString(Object value) {
        String strValue;
        if (value == null) return null;
        if (ClassUtil.isPrimitiveWrapper(value.getClass())) {
            strValue = value.toString();
        } else if (value instanceof Date) {
            strValue = DateUtil.formatDateTime((Date) value);
        } else {
            strValue = JSONUtil.toJsonStr(value);
        }
        return strValue;
    }

    private static <T> T stringToObject(String s, Class<T> clazz) {
        if (StrUtil.isEmpty(s)) return null;
        return JSONUtil.toBean(s, clazz);
    }

}
