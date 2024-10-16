package com.zhuang.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.Assert.*;

public class BigDecimalUtilsTest {

    @Test
    public void test() {
        BigDecimal a = new BigDecimal("0.3");
        BigDecimal b = new BigDecimal("0.4");
        BigDecimal c = BigDecimalUtils.divide(a, b, 4, RoundingMode.HALF_UP);
        System.out.println(c);
        System.out.println(c.stripTrailingZeros());
        System.out.println(c.toPlainString());
    }

    @Test
    public void equals() {
        BigDecimal a = new BigDecimal("0.30");
        BigDecimal b = new BigDecimal("0.3");
        System.out.println(BigDecimalUtils.equals(a, b));
    }

    @Test
    public void equalsZero() {
        BigDecimal a = new BigDecimal("0.0");
        System.out.println(BigDecimalUtils.equalsZero(a));
    }

    @Test
    public void round() {
        System.out.println(BigDecimalUtils.round(new BigDecimal("11.011"), 3));
    }

    @Test
    public void avg() {
        System.out.println(BigDecimalUtils.avg(Arrays.asList(new BigDecimal("1.23"), new BigDecimal("4.33"), new BigDecimal("3.33"))));
    }


    @Test
    public void mid() {
        System.out.println(BigDecimalUtils.mid(Arrays.asList(new BigDecimal("1.23"), new BigDecimal("4.33"), new BigDecimal("3.33"), new BigDecimal("3.34"))));
    }

}