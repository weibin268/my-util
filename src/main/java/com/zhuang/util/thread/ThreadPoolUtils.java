package com.zhuang.util.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                // 核心线程数 (corePoolSize): 这是线程池中始终保持活跃的线程的最小数量，即使这些线程是空闲的。这些核心线程不会因为空闲时间过长而被回收（除非设置了allowCoreThreadTimeOut为true）
                5,
                // 最大线程数 (maximumPoolSize): 这是线程池中允许的最大线程数量。当工作队列已满，并且当前运行的线程数量已经等于corePoolSize时，如果还有新的任务提交，线程池可以临时创建新的线程来处理这些任务，直到线程总数达到maximumPoolSize。
                10,
                // keepAliveTime
                60,
                TimeUnit.SECONDS,
                // workQueue
                new LinkedBlockingQueue<Runnable>(1024),
                //new NamedThreadFactory("test-thread", false),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

}
