package com.zhuang.util;

import lombok.Data;

import java.math.BigDecimal;

public class ChartUtils {

    public static ScaleInfo getScaleInfo(BigDecimal min, BigDecimal max) {
        if (min == null || max == null) return null;
        if (min.compareTo(max) > 0) return null;
        ScaleInfo result = new ScaleInfo();
        BigDecimal d0 = (max.subtract(min)).divide(BigDecimal.valueOf(5));
        if (d0.compareTo(BigDecimal.ZERO) == 0) {
            if (max.compareTo(BigDecimal.ZERO) == 0) {
                result.setMin(BigDecimal.valueOf(-1));
                result.setScale(BigDecimal.valueOf(0.5));
                result.setMax(BigDecimal.valueOf(1));
                return result;
            } else {
                BigDecimal y = max.abs();
                int n = 0;
                while (y.compareTo(BigDecimal.valueOf(1)) < 0) {
                    y = y.multiply(BigDecimal.valueOf(10));
                    n = n - 1;
                }
                while (y.compareTo(BigDecimal.valueOf(10)) >= 0) {
                    y = y.divide(BigDecimal.valueOf(10));
                    n = n + 1;
                }
                BigDecimal d = BigDecimal.valueOf(Math.pow(10, n));
                BigDecimal tempMax = BigDecimal.valueOf(Math.ceil(max.divide(d).doubleValue())).multiply(d);
                BigDecimal tempMin = BigDecimal.valueOf(Math.floor(min.divide(d).doubleValue())).multiply(d);
                result.setMin(tempMin);
                result.setScale(d.multiply(BigDecimal.valueOf(0.2)));
                result.setMax(tempMax);
                return result;
            }
        } else {
            BigDecimal d;
            int n = 0;
            while (d0.compareTo(BigDecimal.valueOf(1)) < 0) {
                d0 = d0.multiply(BigDecimal.valueOf(10));
                n = n - 1;
            }
            while (d0.compareTo(BigDecimal.valueOf(10)) >= 0) {
                d0 = d0.divide(BigDecimal.valueOf(10));
                n = n + 1;
            }
            if (d0.compareTo(BigDecimal.valueOf(1.6)) <= 0) {
                d = BigDecimal.valueOf(1);
            } else if (d0.compareTo(BigDecimal.valueOf(3.2)) <= 0) {
                d = BigDecimal.valueOf(2);
            } else if (d0.compareTo(BigDecimal.valueOf(8)) <= 0) {
                d = BigDecimal.valueOf(5);
            } else {
                d = BigDecimal.valueOf(10);
            }
            d = d.multiply(BigDecimal.valueOf(Math.pow(10, n)));
            BigDecimal tempMax = BigDecimal.valueOf(Math.ceil(max.divide(d).doubleValue())).multiply(d);
            BigDecimal tempMin = BigDecimal.valueOf(Math.floor(min.divide(d).doubleValue())).multiply(d);
            if (BigDecimal.valueOf(0.5).multiply(max).compareTo(min.negate()) <= 0 && min.negate().compareTo(max) < 0) {
                tempMin = tempMax.negate();
            } else if (BigDecimal.valueOf(-0.5).multiply(min).compareTo(max) <= 0 && max.compareTo(min.negate()) < 0) {
                tempMax = tempMin.negate();
            }
            result.setMax(tempMax);
            result.setScale(d);
            result.setMin(tempMin);
        }
        return result;
    }

    public static double getIntervalValue(double max) {
        int index = ((Double) Math.floor(Math.log10(max))).intValue();
        if (max >= (5 * Math.pow(10, index))) {
            return 1 * Math.pow(10, index - 1);
        } else if (max >= (2.5 * Math.pow(10, index)) && max < (5 * Math.pow(10, index))) {
            return 5 * Math.pow(10, index - 2);
        } else {
            return 2 * Math.pow(10, index - 2);
        }
    }

    public static BigDecimal getIntervalValue(BigDecimal max) {
        double intervalValue = getIntervalValue(max.doubleValue());
        return new BigDecimal(intervalValue);
    }

    public static BigDecimal getMaxXValue(BigDecimal max, BigDecimal intervalValue) {
        double xMaxValue = getMaxXValue(max.doubleValue(), intervalValue.doubleValue());
        return new BigDecimal(xMaxValue);
    }

    public static double getMaxXValue(double max, double intervalValue) {
        //ceil（信号最大值/区间间隔）*区间间隔
        return Math.ceil((max / intervalValue) * intervalValue);
    }

    @Data
    public static class ScaleInfo {
        private BigDecimal min;
        private BigDecimal scale;
        private BigDecimal max;
    }
}
