package com.zhuang.util.spring;

import cn.hutool.cache.impl.TimedCache;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryCache implements Cacheable {

    private static Map<String, TimedCache> timedCacheMap = new ConcurrentHashMap<>(1);

    @Override
    public void set(String key, String value, int timeoutSeconds) {
        getTimedCache(key, timeoutSeconds).put(key, value);
    }

    @Override
    public String get(String key) {
        return getTimedCache(key, null).get(key).toString();
    }

    @Override
    public void delete(String key) {
        getTimedCache(key, null).remove(key);
    }

    @Override
    public String getType() {
        return "memory";
    }

    private TimedCache getTimedCache(String key, Integer timeoutSeconds) {
        TimedCache timedCache;
        if (timedCacheMap.containsKey(key)) {
            timedCache = timedCacheMap.get(key);
        } else {
            timedCache = new TimedCache(timeoutSeconds);
            timedCacheMap.put(key, timedCache);
        }
        return timedCache;
    }

}
