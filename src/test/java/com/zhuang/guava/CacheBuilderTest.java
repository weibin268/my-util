package com.zhuang.guava;

import cn.hutool.core.util.RandomUtil;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CacheBuilderTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build(new CacheLoader<String, String>() {
                    public String load(String key) {
                        System.out.println("create by key:" + key);
                        return RandomUtil.randomNumbers(10);
                    }
                });
        for (Integer i = 0; i < 100; i++) {
            Thread.sleep(500);
            System.out.println(cache.get("aaa"));
            System.out.println(cache.get(i.toString()));
        }
    }

}
