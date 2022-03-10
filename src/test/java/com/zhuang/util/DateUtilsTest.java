package com.zhuang.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateUtilsTest {

    @Test
    public void test() {
        DateUtils.handleEachDate("2022-01-01", "2022-02-01", strDate -> {
            System.out.println(strDate);
        });
    }

}
