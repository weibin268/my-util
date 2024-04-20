package com.zhuang.util.queue;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 非阻塞无边界的队列
 */
public class DemoConcurrentQueue {

    private static final ConcurrentLinkedQueue<Element> QUEUE = new ConcurrentLinkedQueue<>();

    public static void offer(Element element) {
        QUEUE.offer(element);
    }

    public static Element poll() {
        return QUEUE.poll();
    }

    public static List<Element> pollAll() {
        List<Element> result = new ArrayList<>();
        Element element = poll();
        while (element != null) {
            result.add(element);
            element = poll();
        }
        return result;
    }

    public static int size() {
        return QUEUE.size();
    }

    public static List<Element> toList() {
        return new ArrayList<>(QUEUE);
    }

    @Data
    public static class Element {
        private String value;
    }
}
