package com.zhuang.util;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadUtilsTest {

    @Test
    public void startFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTaskA = ThreadUtils.startFutureTask(() -> {
            ThreadUtils.sleep(3000);
            return "a";
        });

        ThreadUtils.sleep(3000);
        System.out.println("b");

        String a = futureTaskA.get();
        System.out.println(a);
    }

    @Test
    public void startCompletableFuture()   {
        CompletableFuture<String> futureTaskA = ThreadUtils.startCompletableFuture(() -> {
            System.out.println("begin a");
            ThreadUtils.sleep(3000);
            System.out.println("end a");
            return "a";
        });

        ThreadUtils.sleep(3000);
        System.out.println("b");

        String a = futureTaskA.join();
        System.out.println(a);
    }


}
