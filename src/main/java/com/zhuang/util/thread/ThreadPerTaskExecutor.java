package com.zhuang.util.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

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
