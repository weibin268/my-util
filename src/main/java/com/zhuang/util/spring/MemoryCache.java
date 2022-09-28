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
        TimedCache timedCache = getTimedCache(key, timeoutSeconds);
        if (timedCache != null) {
            getTimedCache(key, timeoutSeconds).put(key, value);
        }
    }

    @Override
    public String get(String key) {
        TimedCache timedCache = getTimedCache(key, null);
        if (timedCache != null) {
            Object oValue = timedCache.get(key);
            return oValue == null ? null : oValue.toString();
        } else {
            return null;
        }
    }

    @Override
    public void delete(String key) {
        TimedCache timedCache = getTimedCache(key, null);
        if (timedCache != null) {
            timedCache.remove(key);
        }
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
            if (timeoutSeconds == null) {
                return null;
            }
            timedCache = new TimedCache(timeoutSeconds);
            timedCacheMap.put(key, timedCache);
        }
        return timedCache;
    }

}
