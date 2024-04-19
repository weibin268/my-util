package com.zhuang.util.queue;

import org.junit.Test;

public class DemoBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {

        for (Integer i = 0; i < 11; i++) {
            DemoBlockingQueue.Demo demo = new DemoBlockingQueue.Demo();
            demo.setValue(i.toString());
            DemoBlockingQueue.put(demo);
        }
        System.out.println("size:" + DemoBlockingQueue.size());
        DemoBlockingQueue.Demo temp = DemoBlockingQueue.take();
        while (temp != null) {
            temp = DemoBlockingQueue.take();
            System.out.println(temp);
        }
    }


}
