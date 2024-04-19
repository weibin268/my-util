package com.zhuang.util.queue;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DemoConcurrentQueueTest {
    @Test
    public void test() {

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

    @Test
    public void test2() {
        for (Integer i = 0; i < 11; i++) {
            DemoConcurrentQueue.Demo demo = new DemoConcurrentQueue.Demo();
            demo.setValue(i.toString());
            DemoConcurrentQueue.offer(demo);
        }
        List<DemoConcurrentQueue.Demo> demos = DemoConcurrentQueue.toList();
        System.out.println(DemoConcurrentQueue.size());
        demos = DemoConcurrentQueue.pollAll();
        System.out.println(DemoConcurrentQueue.size());
    }

}
