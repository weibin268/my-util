package com.zhuang.util;

import org.junit.Test;

import java.util.List;

public class DateUtilsTest {

    @Test
    public void handleEachDate() {
        DateUtils.handleEachDay("2022-01-01", "2022-02-01", strDate -> {
            System.out.println(strDate);
        });
    }

    @Test
    public void handleEachHour() {
        DateUtils.handleEachHour("2022-01-01 00:00:00", "2022-02-01 00:00:00", strDate -> {
            System.out.println(strDate);
        });
    }

    @Test
    public void handleEachMonth() {
        DateUtils.handleEachMonth("2022-01-01", "2022-02-01", strDate -> {
            System.out.println(strDate);
        });
    }

    @Test
    public void getEachMonth() {
        List<String> eachMonthList = DateUtils.getEachMonth("2022-01-01", "2022-02-01");
        System.out.println(eachMonthList);
    }
}
