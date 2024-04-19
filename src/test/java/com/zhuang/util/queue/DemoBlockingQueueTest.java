package com.zhuang.util.queue;

import org.junit.Test;

public class DemoBlockingQueueTest {

    @Test
    public void test() throws InterruptedException {

        for (Integer i = 0; i < 11; i++) {
            DemoBlockingQueue.Element element = new DemoBlockingQueue.Element();
            element.setValue(i.toString());
            DemoBlockingQueue.put(element);
        }
        System.out.println("size:" + DemoBlockingQueue.size());
        DemoBlockingQueue.Element temp = DemoBlockingQueue.take();
        while (temp != null) {
            temp = DemoBlockingQueue.take();
            System.out.println(temp);
        }
    }


}
