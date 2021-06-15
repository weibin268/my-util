package com.zhuang.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
}