package com.zhuang.util.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.function.Supplier;

public class ThreadUtils {

    public static <T> FutureTask<T> startFutureTask(Callable<T> callable) {
        FutureTask<T> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask;
    }

    public static <T> CompletableFuture<T> startCompletableFuture(Supplier<T> supplier) {
        // 设置每个任务创建一个线，默认使用的是ForkJoinPool线程池
        // 注意：慎用该方式，因为使用数据库连接池时，通常会为每个线程分配一个数据库连接，如果线程数量过多，而连接池中的连接数量有限，那么可能会出现线程等待连接的情况
        return CompletableFuture.supplyAsync(supplier, new ThreadPerTaskExecutor());
    }

    public static <T> CompletableFuture<T> startCompletableFuture(Supplier<T> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
