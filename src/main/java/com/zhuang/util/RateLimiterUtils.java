package com.zhuang.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 限流工具
 */
public class RateLimiterUtils {

    private static LoadingCache<String, RateLimiter> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .build(new CacheLoader<String, RateLimiter>() {
                public RateLimiter load(String key) {
                    return RateLimiter.create(5);
                }
            });

    public static RateLimiter getByKey(String key) {
        try {
            return cache.get(key);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
