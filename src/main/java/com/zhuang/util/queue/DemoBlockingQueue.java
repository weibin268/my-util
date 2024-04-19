package com.zhuang.util.queue;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 阻塞的可设置边界的队列
 */
public class DemoBlockingQueue {

    private static final LinkedBlockingQueue<Element> QUEUE = new LinkedBlockingQueue();

    public static void put(Element element) {
        try {
            QUEUE.put(element);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Element take() {
        Element element;
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
    public static class Element {
        private String value;
    }
}
