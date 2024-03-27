package com.zhuang.math;

import org.apache.commons.math3.util.MathArrays;
import org.junit.Test;

public class MathArraysTest {
    @Test
    public void test() {
        double[] a = new double[]{1d, 2d, 3d};
        double[] b = new double[]{1d, 2d, 3d};
        double[] convolve = MathArrays.convolve(a, b);
        for (double v : convolve) {
            System.out.print(v+" ");
        }
    }
}
