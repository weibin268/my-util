package com.zhuang.hutool;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void endOfMonth(){
        System.out.println(DateUtil.endOfMonth(new Date()));
    }

}
