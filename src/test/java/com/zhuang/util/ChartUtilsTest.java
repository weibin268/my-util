package com.zhuang.util;

import org.junit.Test;

import java.math.BigDecimal;

public class ChartUtilsTest {

    @Test
    public void getScaleInfo() {
        ChartUtils.ScaleInfo scaleInfo = ChartUtils.getScaleInfo(BigDecimal.valueOf(22), BigDecimal.valueOf(22));
        System.out.println(scaleInfo);
    }

    @Test
    public void getIntervalValue() {
        System.out.println(ChartUtils.getIntervalValue(333));
    }


    @Test
    public void getMaxXValue() {
        System.out.println(ChartUtils.getMaxXValue(333.132, 3.5));
    }

}
