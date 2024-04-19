package com.zhuang.util.queue;

import lombok.Data;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 非阻塞无边界的队列
 */
public class DemoConcurrentQueue {

    private static final ConcurrentLinkedQueue<Demo> queue = new ConcurrentLinkedQueue();

    public static void offer(Demo element) {
        queue.offer(element);
    }

    public static Demo poll() {
        Demo element = queue.poll();
        return element;
    }

    public static int size() {
        return queue.size();
    }

    @Data
    public static class Demo {
        private String value;
    }
}
