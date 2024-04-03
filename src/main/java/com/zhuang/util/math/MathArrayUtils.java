package com.zhuang.util.math;

import cn.hutool.core.util.ArrayUtil;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static double[] add(double[] a, double n) {
        return operate(a, n, "+");
    }

    public static double[] subtract(double[] a, double n) {
        return operate(a, n, "-");
    }

    public static double[] multiply(double[] a, double n) {
        return operate(a, n, "*");
    }

    public static double[] divide(double[] a, double n) {
        return operate(a, n, "/");
    }

    public static double[] power(double[] a, double n) {
        return operate(a, n, "^");
    }

    private static double[] operate(double[] a, double n, String mode) {
        double[] r = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            if (mode.equals("+")) {
                r[i] = a[i] + n;
            } else if (mode.equals("-")) {
                r[i] = a[i] - n;
            } else if (mode.equals("*")) {
                r[i] = a[i] * n;
            } else if (mode.equals("/")) {
                r[i] = a[i] / n;
            } else if (mode.equals("^")) {
                r[i] = Math.pow(a[i], n);
            }
        }
        return r;
    }

    /**
     * 多项式曲线拟合
     *
     * @param x
     * @param y
     * @param degree
     * @return
     */
    public static double[] fit4PolynomialCurve(double[] x, double[] y, int degree) {
        // Create a default curve fitter with zero initial guess for coefficients
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
        // Fit the data points
        WeightedObservedPoint[] observations = new WeightedObservedPoint[x.length];
        for (int i = 0; i < x.length; i++) {
            observations[i] = new WeightedObservedPoint(1.0, x[i], y[i]);
        }
        double[] coefficients = fitter.fit(Arrays.asList(observations));
        return coefficients;
    }

    public static void fill4PolynomialCurve(double[] d, int win, int degree) {
        List<Integer> nanIndexList = new ArrayList<>();
        for (int i = 0; i < d.length; i++) {
            if (Double.isNaN(d[i]))
                nanIndexList.add(i);
        }
        for (int j = 0; j < nanIndexList.size(); j++) {
            int idxL = Math.max(0, nanIndexList.get(j) - win / 2);
            int idxU = Math.min(d.length - 1, nanIndexList.get(j) + win / 2);
            List<Double> x = new ArrayList<>();
            List<Double> y = new ArrayList<>();
            int nanLen = 0, maxNanLen = 0;
            for (int k = idxL; k <= idxU; k++) {
                if (!nanIndexList.contains(k)) {
                    x.add(Double.valueOf(k));
                    y.add(d[k]);
                    nanLen = 0;
                } else {
                    nanLen++;
                    if (nanLen > maxNanLen)
                        maxNanLen = nanLen;
                }
            }
            if (maxNanLen < (idxU - idxL + 1) / 2 && x.size() > (idxU - idxL + 1) / 2) {
                double[] xMatrix = ArrayUtil.unWrap(x.toArray(new Double[0]));
                double[] yMatrix = ArrayUtil.unWrap(y.toArray(new Double[0]));
                double[] p = fit4PolynomialCurve(xMatrix, yMatrix, degree);
                double insertData = 0d;
                for (int k = 0; k < p.length; k++) {
                    insertData += Math.pow(nanIndexList.get(j), k) * p[k];
                }
                d[nanIndexList.get(j)] = insertData;
            }
        }
    }
}
