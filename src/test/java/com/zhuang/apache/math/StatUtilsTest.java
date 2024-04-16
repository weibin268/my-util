package com.zhuang.apache.math;

import org.apache.commons.math3.stat.StatUtils;
import org.junit.Test;

import java.util.Arrays;

public class StatUtilsTest {

    @Test
    public void test() {
        double[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        double mean = StatUtils.mean(array);
        double variance = StatUtils.variance(array);
        double[] normalize = StatUtils.normalize(array);
        System.out.println("归一化后的数组：" + Arrays.toString(normalize));
    }
}
