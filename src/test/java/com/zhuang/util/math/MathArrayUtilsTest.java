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

    @Test
    public void fit4PolynomialCurve() {
        double[] a = {-20, -15, -10, -5, 0, 5, 10, 15, 20, 25};
        double[] b = {-0.0933, -0.0978, -0.0982, -0.0784, -0.0489, -0.0066, 0.049, 0.1072, 0.1283, 0.13};
        double[] r = MathArrayUtils.fit4PolynomialCurve(a, b, 9);
        for (double v : r) {
            System.out.println(v);
        }
    }

}
