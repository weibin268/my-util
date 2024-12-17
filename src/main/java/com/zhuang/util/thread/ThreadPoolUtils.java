package com.zhuang.util.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class ThreadPoolUtils {

    private static ThreadPoolExecutor threadPoolExecutor;

    public static synchronized ThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    // 核心线程数（corePoolSize）：这是线程池中始终保持活动的线程数量，即使它们是空闲的，如果提交的任务数超过了核心线程数，那么额外的任务会被放入队列中等待
                    5,
                    // 最大线程数（maximumPoolSize）：如果队列已满且当前线程数小于最大线程数，线程池会创建新的线程来执行任务
                    10,
                    // 保持活动时间（keepAliveTime）：当线程数超过核心线程数时，这时多余空闲线程在终止前可以保持空闲的最长时间
                    60,
                    // 时间单位（unit）：keepAliveTime 参数的时间单位
                    TimeUnit.SECONDS,
                    // 工作队列（workQueue）：用于保存等待执行的任务的阻塞队列
                    new LinkedBlockingQueue<>(1024), // 默认无界队列，当运行线程大于corePoolSize时始终放入此队列，此时maxPoolSize无效。当构造LinkedBlockingQueue对象时传入参数，变为有界队列，队列满时，运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
                    //new ArrayBlockingQueue<>(1024), // 有界队列，相对无界队列有利于控制队列大小，队列满时，运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
                    //new SynchronousQueue<>(false), // 它将任务直接提交给线程而不保持它们。当运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
                    // 线程工厂（threadFactory）：用于创建新线程的工厂
                    //new NamedThreadFactory("test-thread", false),
                    // 拒绝策略（handler）：当任务太多以至于无法及时处理时，线程池采取的策略
                    //new ThreadPoolExecutor.DiscardPolicy() // 不做任何处理
                    //new ThreadPoolExecutor.AbortPolicy() // 抛出异常(submit报异常)
                    //new ThreadPoolExecutor.CallerRunsPolicy() // 由调用方执行
                    ((r, executor) -> {
                        log.error("Thread pool rejected execution -> activeCount=" + executor.getActiveCount());
                    })
            );
            // 设置核心线程可以回收（线程空闲时间超过keepAliveTime就回收）
            //threadPoolExecutor.allowCoreThreadTimeOut(true);
        }
        return threadPoolExecutor;
    }

}
