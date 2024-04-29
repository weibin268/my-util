package com.zhuang.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

public class ThreadUtilsTest {

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
        List<CompletableFuture<String>> taskList = Arrays.asList("a", "b", "c").stream().map(c -> ThreadUtils.startCompletableFuture(() -> {
            System.out.println(c + " begin");
            if (c.equals("b")) {
                ThreadUtils.sleep(3000);
            }
            System.out.println(c + " end");
            return "result:" + c;
        })).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        for (CompletableFuture<String> task : taskList) {
            String taskResult = task.join();
            result.add(taskResult);
        }
        result.forEach(System.out::println);
    }


}
