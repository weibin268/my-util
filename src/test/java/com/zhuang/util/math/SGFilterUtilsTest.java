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
        double[] d = new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        double[] doubles = SGFilterUtils.filter(d, 1, 3);
        System.out.println(doubles);
    }

}
