package com.zhuang.util.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                5,   // corePoolSize
                10,             // maximumPoolSize
                60,             // keepAliveTime
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024),    //workQueue
                //new NamedThreadFactory("test-thread", false),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

}
