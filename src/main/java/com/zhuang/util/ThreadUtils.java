package com.zhuang.util;

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
        return CompletableFuture.supplyAsync(supplier);
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
