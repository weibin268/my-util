package com.zhuang.util.spring.cache;

import com.zhuang.util.spring.RedisUtils;
import org.springframework.stereotype.Component;

@Component
public class RedisCache implements Cacheable {

    @Override
    public void set(String key, String value, int timeoutSeconds) {
        RedisUtils.set(key, value, timeoutSeconds);
    }

    @Override
    public String get(String key) {
        return RedisUtils.get(key);
    }

    @Override
    public void delete(String key) {
        RedisUtils.delete(key);
    }

    @Override
    public String getType() {
        return "redis";
    }
}
