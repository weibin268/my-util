package com.zhuang.util.queue;

import org.junit.Test;

import static org.junit.Assert.*;

public class DemoConcurrentQueueTest {
    @Test
    public void test() throws InterruptedException {

        for (Integer i = 0; i < 11; i++) {
            DemoConcurrentQueue.Demo demo = new DemoConcurrentQueue.Demo();
            demo.setValue(i.toString());
            DemoConcurrentQueue.offer(demo);
        }
        System.out.println("size:" + DemoConcurrentQueue.size());
        DemoConcurrentQueue.Demo temp = DemoConcurrentQueue.poll();
        while (temp != null) {
            temp = DemoConcurrentQueue.poll();
            System.out.println(temp);
        }
    }
}
