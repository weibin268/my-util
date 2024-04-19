package com.zhuang.util.queue;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞的可设置边界的队列
 */
public class DemoBlockingQueue {

    private static final LinkedBlockingQueue<Demo> QUEUE = new LinkedBlockingQueue();

    public static void put(Demo element) {
        try {
            QUEUE.put(element);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Demo take() {
        Demo element;
        try {
            element = QUEUE.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return element;
    }

    public static int size() {
        return QUEUE.size();
    }

    @Data
    public static class Demo {
        private String value;
    }
}
