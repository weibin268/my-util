package com.zhuang.util.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return new ThreadPoolExecutor(
                // 核心线程数（corePoolSize）：这是线程池中始终保持活动的线程数量，即使它们是空闲的，如果提交的任务数超过了核心线程数，那么额外的任务会被放入队列中等待
                5,
                // 最大线程数（maximumPoolSize）：如果队列已满且当前线程数小于最大线程数，线程池会创建新的线程来执行任务
                10,
                // 保持活动时间（keepAliveTime）：当线程数超过核心线程数时，这是多余空闲线程在终止前可以保持空闲的最长时间
                60,
                // 时间单位（unit）：keepAliveTime 参数的时间单位
                TimeUnit.SECONDS,
                // 工作队列（workQueue）：用于保存等待执行的任务的阻塞队列
                new LinkedBlockingQueue<Runnable>(1024),
                // 线程工厂（threadFactory）：用于创建新线程的工厂
                //new NamedThreadFactory("test-thread", false),
                // 拒绝策略（handler）：当任务太多以至于无法及时处理时，线程池采取的策略
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }

}
