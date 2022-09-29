package com.zhuang.util.spring.cache;

import cn.hutool.core.thread.ThreadUtil;
import com.zhuang.util.spring.cache.MemoryCache;
import org.junit.Test;

public class MemoryCacheTest {

    @Test
    public void test() {

        MemoryCache memoryCache = new MemoryCache();
        memoryCache.set("test", "test", 1);
        System.out.println(memoryCache.get("test"));
        ThreadUtil.sleep(1000);
        System.out.println(memoryCache.get("test"));
        System.out.println("end");

    }

}
