package com.zhuang.util.queue;

import org.junit.Test;

import java.util.List;

public class DemoConcurrentQueueTest {
    @Test
    public void test() {

        for (Integer i = 0; i < 11; i++) {
            DemoConcurrentQueue.Element element = new DemoConcurrentQueue.Element();
            element.setValue(i.toString());
            DemoConcurrentQueue.offer(element);
        }
        System.out.println("size:" + DemoConcurrentQueue.size());
        DemoConcurrentQueue.Element temp = DemoConcurrentQueue.poll();
        while (temp != null) {
            temp = DemoConcurrentQueue.poll();
            System.out.println(temp);
        }
    }

    @Test
    public void test2() {
        for (Integer i = 0; i < 11; i++) {
            DemoConcurrentQueue.Element element = new DemoConcurrentQueue.Element();
            element.setValue(i.toString());
            DemoConcurrentQueue.offer(element);
        }
        List<DemoConcurrentQueue.Element> elements = DemoConcurrentQueue.toList();
        System.out.println(DemoConcurrentQueue.size());
        elements = DemoConcurrentQueue.pollAll();
        System.out.println(DemoConcurrentQueue.size());
    }

}
