package com.zhuang.util.spring.cache;

import cn.hutool.core.thread.ThreadUtil;
import com.zhuang.util.cache.MemoryCache;
import org.junit.Test;

public class MemoryCacheTest {

    @Test
    public void test() {
        String key = "test";
        MemoryCache memoryCache = new MemoryCache();
        memoryCache.set(key, "test", 1);
        System.out.println(memoryCache.get(key));
        ThreadUtil.sleep(1000);
        System.out.println(memoryCache.get(key));
        memoryCache.delete(key);
        System.out.println(memoryCache.get(key));
        System.out.println("end");

    }

}
