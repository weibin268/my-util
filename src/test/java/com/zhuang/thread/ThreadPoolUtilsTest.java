package com.zhuang.thread;

import cn.hutool.core.thread.ThreadUtil;
import com.zhuang.util.thread.ThreadPoolUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ThreadPoolUtilsTest {

    @Test
    public void test() throws IOException {
        ThreadPoolExecutor threadPoolExecutor =  ThreadPoolUtils.getThreadPoolExecutor();
        IntStream.range(0, 100).forEach(i -> {
            ThreadUtil.sleep(100, TimeUnit.MILLISECONDS);
            threadPoolExecutor.execute(() -> {
                System.out.println("i=" + i);
                ThreadUtil.sleep(30, TimeUnit.SECONDS);
            });
        });

        System.in.read();
    }

}