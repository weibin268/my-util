package com.zhuang.util;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadUtils {

    public static <T> FutureTask<T> startFutureTask(Callable<T> callable) {
        FutureTask<T> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        return futureTask;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
