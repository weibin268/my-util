package com.zhuang.apache.math;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.math3.util.MathArrays;
import org.junit.Test;

import java.math.BigDecimal;

public class MathArraysTest {
    @Test
    public void test() {
        double[] a = {1, 2, 3, 4};
        double[] b = {0, 1, 2};
        b = ArrayUtil.reverse(b);
        double[] convolve = MathArrays.convolve(a, b);
        for (double v : convolve) {
            System.out.print(v+" ");
        }
    }
}
