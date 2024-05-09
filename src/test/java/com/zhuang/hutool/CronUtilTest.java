package com.zhuang.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.cron.CronUtil;
import org.junit.Test;

import java.util.Date;

public class CronUtilTest {

    @Test
    public void test() throws InterruptedException {

        CronUtil.setMatchSecond(true);
        CronUtil.start();
        CronUtil.schedule("001", "0/1 * * * * ? *", () -> {
            System.out.println("thread:" + Thread.currentThread().getName() + " -> " + DateUtil.formatDateTime(new Date()));
        });
        CronUtil.schedule("002", "0/1 * * * * ? *", () -> {
            System.out.println("thread:" + Thread.currentThread().getName() + " -> " + DateUtil.formatDateTime(new Date()));
        });
        CronUtil.getScheduler().getTaskTable().getIds().forEach(System.out::println);
        Thread.sleep(1000 * 60);
    }
}
