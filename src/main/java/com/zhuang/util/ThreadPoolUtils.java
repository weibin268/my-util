package com.zhuang.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                5,      // corePoolSize
                10,                 // maximumPoolSize
                60,                 // keepAliveTime
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(50)     // workQueue
                //new LinkedBlockingQueue<Runnable>()
                , new ThreadPoolExecutor.DiscardPolicy()  // rejected handler
        );
    }

}
