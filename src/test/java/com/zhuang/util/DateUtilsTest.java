package com.zhuang.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void handleEachDate() {
        DateUtils.handleEachDate("2022-01-01", "2022-02-01", strDate -> {
            System.out.println(strDate);
        });
    }

    @Test
    public void handleEachHour() {
        DateUtils.handleEachHour("2022-01-01 00:00:00", "2022-02-01 00:00:00", strDate -> {
            System.out.println(strDate);
        });
    }
}
