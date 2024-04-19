package com.zhuang.util.queue;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

/**
 * 非阻塞无边界的队列
 */
public class DemoConcurrentQueue {

    private static final ConcurrentLinkedQueue<Demo> QUEUE = new ConcurrentLinkedQueue();

    public static void offer(Demo element) {
        QUEUE.offer(element);
    }

    public static Demo poll() {
        Demo element = QUEUE.poll();
        return element;
    }

    public static List<Demo> pollAll() {
        List<Demo> result = new ArrayList<>();
        Demo element = poll();
        while (element != null) {
            result.add(element);
            element = poll();
        }
        return result;
    }

    public static int size() {
        return QUEUE.size();
    }

    public static List<Demo> toList() {
        return QUEUE.stream().collect(Collectors.toList());
    }

    @Data
    public static class Demo {
        private String value;
    }
}
