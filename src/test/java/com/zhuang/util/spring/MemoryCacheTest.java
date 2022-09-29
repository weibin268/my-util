package com.zhuang.util.spring;

import cn.hutool.core.thread.ThreadUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class MemoryCacheTest {

    @Test
    public void test() {

        MemoryCache memoryCache = new MemoryCache();
        memoryCache.set("test", "test", 2);
        System.out.println(memoryCache.get("test"));
        ThreadUtil.sleep(1000);
        System.out.println(memoryCache.get("test"));
        System.out.println("end");

    }

}
