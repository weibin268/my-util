package com.zhuang.util.spring.cache;

import cn.hutool.cache.impl.TimedCache;
import org.springframework.stereotype.Component;

@Component
public class MemoryCache extends TimedCache<String, String> implements Cacheable {

    public MemoryCache() {
        super(10 * 1000L);
        //注意：这里需要设置自动清理，不然就算缓存过期，内存也不会被回收
        schedulePrune(10 * 1000L);
    }

    @Override
    public void set(String key, String value, int timeoutSeconds) {
        super.put(key, value, timeoutSeconds * 1000L);
    }

    @Override
    public String get(String key) {
        return super.get(key);
    }

    @Override
    public void delete(String key) {
        super.remove(key);
    }

    @Override
    public String getType() {
        return "memory";
    }

}
