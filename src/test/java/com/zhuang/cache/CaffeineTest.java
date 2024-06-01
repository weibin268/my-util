package com.zhuang.cache;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.junit.Test;

import java.time.Duration;
import java.util.Date;

public class CaffeineTest {

    @Test
    public void test() {
        LoadingCache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(Duration.ofSeconds(5))
                .refreshAfterWrite(Duration.ofSeconds(3))
                .build(key -> {
                    if (key.equals("date")) {
                        return new Date();
                    }
                    return null;
                });
        cache.put("date", new Date());
        for (int i = 0; i < 20; i++) {
            System.out.println(DateUtil.formatDateTime((Date) cache.get("date")));
            ThreadUtil.sleep(1000);
        }

    }
}
