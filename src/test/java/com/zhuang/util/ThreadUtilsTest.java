package com.zhuang.util;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadUtilsTest {

    @Test
    public void test() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTaskA = ThreadUtils.startFutureTask(() -> {
            ThreadUtils.sleep(3000);
            return "a";
        });

        ThreadUtils.sleep(3000);
        System.out.println("b");

        String a = futureTaskA.get();
        System.out.println(a);
    }

}