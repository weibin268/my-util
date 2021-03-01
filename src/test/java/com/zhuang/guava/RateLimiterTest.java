package com.zhuang.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

public class RateLimiterTest {

    //每秒生产5个令牌
    private static RateLimiter limiter = RateLimiter.create(5);

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            if (limiter.tryAcquire()) {
                System.out.println("执行成功！");
            } else {
                System.out.println("系统繁忙！");
            }
        }
    }

}
