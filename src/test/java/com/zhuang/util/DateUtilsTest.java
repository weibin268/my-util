package com.zhuang.util;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;
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
    public void handleEachMinute() {
        DateUtils.handleEachMinute("2022-02-01 00:00:00", "2022-02-02 00:00:00", strDate -> {
            System.out.println(strDate);
        });
        System.out.println("----------------------------------------------------------------------------------------------------");
        DateUtils.handleEachMinute(DateUtil.parseDateTime("2022-02-01 00:00:00"), DateUtil.parseDateTime("2022-02-02 00:00:00"), date -> {
            System.out.println(date);
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

        List<Date> eachMonthList2 = DateUtils.getEachMonth(DateUtil.parseDate("2022-01-01"), new Date());
        System.out.println(eachMonthList2);
    }

    @Test
    public void parseTimesToList() {
        List<String> eachMonthList = DateUtils.parseTimesToList("8:0,01:00-02:00");
        System.out.println(eachMonthList);
    }
}
