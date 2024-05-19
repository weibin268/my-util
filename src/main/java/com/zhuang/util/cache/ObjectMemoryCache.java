package com.zhuang.util.cache;

import cn.hutool.cache.impl.TimedCache;
import org.springframework.stereotype.Component;

@Component
public class ObjectMemoryCache extends TimedCache<String, Object> {

    public ObjectMemoryCache() {
        super(10 * 1000L);
        //注意：这里需要设置自动清理，不然就算缓存过期，内存也不会被回收
        schedulePrune(10 * 1000L);
    }

    public void set(String key, Object value, int timeoutSeconds) {
        super.put(key, value, timeoutSeconds * 1000L);
    }

    public Object get(String key) {
        return super.get(key, false);
    }

    public void delete(String key) {
        super.remove(key);
    }
}
