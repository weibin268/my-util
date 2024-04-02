package com.zhuang.util.math;

import org.junit.Test;

public class SGFilterUtilsTest {

    @Test
    public void savgolCoeffs() {
        double[] doubles = SGFilterUtils.savgolCoeffs(3, 3, -1, true);
        System.out.println(doubles);
    }

    @Test
    public void filter() {
        double[] d = new double[]{1, 3, 5, 3, 8, 3, 5};
        double[] doubles = SGFilterUtils.filter(d, 7, 2);
        System.out.println(doubles);
    }

}
