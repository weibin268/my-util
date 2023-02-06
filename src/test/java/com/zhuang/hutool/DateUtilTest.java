package com.zhuang.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.thread.ThreadUtil;
import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void endOfMonth() {
        System.out.println(DateUtil.endOfMonth(new Date()));
    }

    @Test
    public void timer() {
        TimeInterval timer = DateUtil.timer();
        ThreadUtil.sleep(1000);
        System.out.println(timer.intervalMs());
    }
}
