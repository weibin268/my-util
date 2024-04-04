package com.zhuang.apache.math;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.junit.Test;

public class MatrixUtilsTest {

    @Test
    public void transpose() {
        RealMatrix m = MatrixUtils.createRealMatrix(new double[][]{{1, 2, 3}, {4, 5, 6}});
        RealMatrix m2 = m.transpose();
        System.out.println(m2);
    }


    @Test
    public void multiply() {
        RealMatrix m = MatrixUtils.createRealMatrix(new double[][]{{1, 2}, {3, 4}});
        RealMatrix m2 = m.power(2);
        System.out.println(m2);
    }

}
