package com.zhuang.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalUtils {

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, 4, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        return divide(a, b, scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        if (a == null || b == null) return null;
        if (b.compareTo(BigDecimal.ZERO) == 0) return null;
        return a.divide(b, scale, roundingMode);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        return a.multiply(b);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        return a.subtract(b);
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        return a.add(b);
    }

    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }

    public static boolean equalsZero(BigDecimal a) {
        if (a == null) return false;
        return a.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 保留有效数字
     *
     * @param num
     * @param precision
     * @return
     */
    public static BigDecimal round(BigDecimal num, int precision) {
        return round(num, precision, RoundingMode.HALF_UP);
    }

    /**
     * 保留有效数字
     *
     * @param num
     * @param precision
     * @param roundingMode
     * @return
     */
    public static BigDecimal round(BigDecimal num, int precision, RoundingMode roundingMode) {
        if (num == null) return null;
        return num.round(new MathContext(precision, roundingMode));
    }

    /**
     * 保留小数位数
     *
     * @param num
     * @param scale
     * @return
     */
    public static BigDecimal scale(BigDecimal num, int scale) {
        return scale(num, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留小数位数
     *
     * @param num
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal scale(BigDecimal num, int scale, RoundingMode roundingMode) {
        if (num == null) return null;
        return num.setScale(scale, roundingMode);
    }
}
