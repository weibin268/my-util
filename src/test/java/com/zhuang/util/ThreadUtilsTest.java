package com.zhuang.util;

import cn.hutool.core.thread.NamedThreadFactory;
import io.netty.util.concurrent.ThreadPerTaskExecutor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThreadUtilsTest {


    private static final ThreadPoolExecutor executor = ThreadPoolUtils.getThreadPoolExecutor();

    @Test
    public void startFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> taskA = ThreadUtils.startFutureTask(() -> {
            ThreadUtils.sleep(3000);
            return "a";
        });
        ThreadUtils.sleep(3000);
        System.out.println("b");
        String a = taskA.get();
        System.out.println(a);
    }

    @Test
    public void startCompletableFuture() {
        List<CompletableFuture<String>> taskList = Stream.of("a", "b", "c").map(c -> ThreadUtils.startCompletableFuture(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + c + " begin");
            if (c.equals("b")) {
                ThreadUtils.sleep(3000);
            }
            System.out.println(Thread.currentThread().getName() + ":" + c + " end");
            return Thread.currentThread().getName() + ":" + "result -> " + c;
        }, new ThreadPerTaskExecutor(new NamedThreadFactory("test-thread", false)))).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for (CompletableFuture<String> task : taskList) {
            String taskResult = task.join();
            result.add(taskResult);
        }
        result.forEach(System.out::println);
    }


}
