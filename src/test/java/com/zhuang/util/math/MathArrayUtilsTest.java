package com.zhuang.util.math;

import org.junit.Test;

public class MathArrayUtilsTest {

    @Test
    public void convolve() {
        double[] a = {1, 2, 3, 4};
        double[] b = {0, 1, 2};
        double[] convolve = MathArrayUtils.convolve(a, b, MathArrayUtils.CONVOLVE_MODE_3);
        for (double v : convolve) {
            System.out.print(v + " ");
        }
    }

}
