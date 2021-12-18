package com.zhuang.util;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ChartUtilsTest {

    @Test
    public void getScaleInfo() {
        ChartUtils.ScaleInfo scaleInfo = ChartUtils.getScaleInfo(BigDecimal.valueOf(22), BigDecimal.valueOf(22));
        System.out.println(scaleInfo);
    }

}
