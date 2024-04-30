package com.zhuang.util;

import cn.hutool.core.thread.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                1,   // corePoolSize
                10,             // maximumPoolSize
                60,             // keepAliveTime
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024),    //workQueue
                new NamedThreadFactory("test-thread-%d", false),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

}
