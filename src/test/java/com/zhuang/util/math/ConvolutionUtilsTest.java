package com.zhuang.util.math;

import org.junit.Test;

public class ConvolutionUtilsTest {

    @Test
    public void test() {
        double[] a = {1, 2, 3, 4};
        double[] b = {0, 1, 2};
        //b = ArrayUtil.reverse(b);
        double[] convolve = ConvolutionUtils.convolve(a, b, 3);
        for (double v : convolve) {
            System.out.print(v + " ");
        }
    }

}
