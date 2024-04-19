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

    private static final ConcurrentLinkedQueue<Element> QUEUE = new ConcurrentLinkedQueue();

    public static void offer(Element element) {
        QUEUE.offer(element);
    }

    public static Element poll() {
        Element element = QUEUE.poll();
        return element;
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
        return QUEUE.stream().collect(Collectors.toList());
    }

    @Data
    public static class Element {
        private String value;
    }
}
