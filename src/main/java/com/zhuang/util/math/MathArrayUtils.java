package com.zhuang.util.math;

import cn.hutool.core.util.ArrayUtil;

public class MathArrayUtils {

    // 输出长度：|m-n|+1，例如：a=[1,2,3,4],b=[0,1,2],r=[8,11]
    public static int CONVOLVE_MODE_1 = 1;
    // 输出长度：max(m,n)，例如：a=[1,2,3,4],b=[0,1,2],r=[5,8,11,4]
    public static int CONVOLVE_MODE_2 = 2;
    // 输出长度：m+n-1，例如：a=[1,2,3,4],b=[0,1,2],r=[2,5,8,11,4,0]
    public static int CONVOLVE_MODE_3 = 3;


    /**
     * mode:1=valid;2=same;3=full;
     *
     * @param a
     * @param b
     * @param mode
     * @return
     */
    public static double[] convolve(double[] a, double[] b, int mode) {
        double[] result = null;
        int m = a.length;
        int n = b.length;
        if (mode == 1) {
            int dim = Math.abs(m - n) + 1;
            result = new double[dim];
            double[] shortData = m <= n ? a : b;
            double[] longData = m <= n ? b : a;
            int min = Math.min(m, n);
            for (int i = 0; i < dim; i++) {
                double sum = 0d;
                for (int j = 0; j < min; j++) {
                    sum += shortData[j] * longData[j + dim - 1 - i];
                }
                if (m < n) {
                    result[i] = sum;
                } else {
                    result[dim - 1 - i] = sum;
                }

            }
        } else if (mode == 2) {
            if (m > n) {
                result = convolve(b, a, CONVOLVE_MODE_2);
                result = ArrayUtil.reverse(result);
            } else {
                result = new double[n];
                int r = (m - 1) / 2 + (m - 1) % 2;
                for (int i = 0; i < n; i++) {
                    double sum = 0d;
                    for (int j = 0; j < m; j++) {
                        if (j + n - 1 - i - r >= 0 && j + n - 1 - i - r < n)
                            sum += a[j] * b[j + n - 1 - i - r];
                    }
                    if (n == m) {
                        result[n - 1 - i] = sum;
                    } else {
                        result[i] = sum;
                    }
                }
            }
        } else if (mode == 3) {
            if (m > n) {
                result = convolve(b, a, CONVOLVE_MODE_3);
                result = ArrayUtil.reverse(result);
            } else {
                int dim = m + n - 1;
                result = new double[m + n - 1];
                for (int i = 0; i < dim; i++) {
                    double sum = 0d;
                    for (int j = 0; j < m; j++) {
                        if (j + n - 1 - i >= 0 && j + n - 1 - i < n) {
                            sum += a[j] * b[j + n - 1 - i];
                        }
                    }
                    result[i] = sum;
                }
            }
        }
        return result;
    }


}
