package com.zhuang.util.spring;

import cn.hutool.cache.impl.TimedCache;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryCache implements Cacheable {

    private static Map<String, MyTimedCache> timedCacheMap = new ConcurrentHashMap<>(1);

    @Override
    public void set(String key, String value, int timeoutSeconds) {
        MyTimedCache timedCache = getTimedCache(key, timeoutSeconds);
        if (timedCache != null) {
            getTimedCache(key, timeoutSeconds).put(key, value);
        }
    }

    @Override
    public String get(String key) {
        MyTimedCache timedCache = getTimedCache(key, null);
        if (timedCache != null) {
            Object oValue = timedCache.get(key);
            return oValue == null ? null : oValue.toString();
        } else {
            return null;
        }
    }

    @Override
    public void delete(String key) {
        MyTimedCache timedCache = getTimedCache(key, null);
        if (timedCache != null) {
            timedCache.remove(key);
        }
    }

    @Override
    public String getType() {
        return "memory";
    }

    private MyTimedCache getTimedCache(String key, Integer timeoutSeconds) {
        MyTimedCache timedCache;
        if (timedCacheMap.containsKey(key)) {
            timedCache = timedCacheMap.get(key);
        } else {
            if (timeoutSeconds == null) {
                return null;
            }
            timedCache = new MyTimedCache(timeoutSeconds, timedCacheMap);
            timedCacheMap.put(key, timedCache);
        }
        return timedCache;
    }

    public static class MyTimedCache<K, V> extends TimedCache<K, V> {

        private Map<String, MyTimedCache> map;

        public MyTimedCache(int timeoutSeconds, Map<String, MyTimedCache> map) {
            super(timeoutSeconds * 1000L);
            this.map = map;
        }

        @Override
        public void onRemove(K key, V cachedObject) {
            this.map.remove(key);
        }
    }

}
