package com.zhuang.thread;

import cn.hutool.core.thread.ThreadUtil;
import com.zhuang.util.thread.ThreadPoolUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolUtilsTest {

    @Test
    public void execute() throws IOException {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.getThreadPoolExecutor();
        IntStream.range(0, 100).forEach(i -> {
            ThreadUtil.sleep(100, TimeUnit.MILLISECONDS);
            threadPoolExecutor.execute(() -> {
                System.out.println("i=" + i);
                ThreadUtil.sleep(30, TimeUnit.SECONDS);
            });
        });

        System.in.read();
    }

    @Test
    public void submit() throws IOException, ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.getThreadPoolExecutor();
        List<Future<Integer>> futureList = new ArrayList<>();
        IntStream.range(0, 100).forEach(i -> {
            ThreadUtil.sleep(100, TimeUnit.MILLISECONDS);
            Future<Integer> future = threadPoolExecutor.submit(() -> {
                System.out.println("[" + Thread.currentThread().getName() + "] i=" + i);
                ThreadUtil.sleep(30, TimeUnit.SECONDS);
                return i;
            });
            futureList.add(future);
        });
        for (Future<Integer> future : futureList) {
            Integer i = future.get();
            System.out.println("[" + Thread.currentThread().getName() + "] result:" + i);
        }
        System.in.read();
    }

}