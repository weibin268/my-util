package com.zhuang.util.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 每个任务创建一个线程
 * 慎用：因为使用数据库连接池时，通常会为每个线程分配一个数据库连接，如果线程数量过多，而连接池中的连接数量有限，那么可能会出现线程等待连接的情况
 */
public final class ThreadPerTaskExecutor implements Executor {
    private final ThreadFactory threadFactory;

    public ThreadPerTaskExecutor() {
        this(null);
    }

    public ThreadPerTaskExecutor(ThreadFactory threadFactory) {
        if (threadFactory == null) {
            this.threadFactory = Executors.defaultThreadFactory();
        } else {
            this.threadFactory = threadFactory;
        }
    }

    public void execute(Runnable command) {
        this.threadFactory.newThread(command).start();
    }

}
