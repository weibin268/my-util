package com.zhuang.util.queue;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞的可设置边界的队列
 */
public class DemoBlockingQueue {

    private static final LinkedBlockingQueue<Demo> queue = new LinkedBlockingQueue();

    public static void put(Demo element) {
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static Demo take() {
        Demo element;
        try {
            element = queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return element;
    }

    @Data
    public static class Demo {
        private String value;
    }
}
