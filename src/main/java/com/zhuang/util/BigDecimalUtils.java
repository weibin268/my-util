package com.zhuang.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class BigDecimalUtils {

    // 默认保留小数位数
    private static final int DEFAULT_SCALE = 4;

    /**
     * 加
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        return a.add(b);
    }

    /**
     * 减
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        return a.subtract(b);
    }

    /**
     * 乘
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return null;
        return a.multiply(b);
    }

    /**
     * 除
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 除
     *
     * @param a
     * @param b
     * @param scale
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale) {
        return divide(a, b, scale, RoundingMode.HALF_UP);
    }

    /**
     * 除
     *
     * @param a
     * @param b
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        if (a == null || b == null) return null;
        if (b.compareTo(BigDecimal.ZERO) == 0) return null;
        return a.divide(b, scale, roundingMode);
    }

    /**
     * 判断两个数是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }

    /**
     * 判断一个数是否等于0
     *
     * @param a
     * @return
     */
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

    /**
     * 求和
     *
     * @param numList
     * @return
     */
    public static BigDecimal sum(Collection<BigDecimal> numList) {
        return numList.stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 最小值
     *
     * @param numList
     * @return
     */
    public static BigDecimal min(Collection<BigDecimal> numList) {
        return numList.stream().filter(Objects::nonNull).min(BigDecimal::compareTo).orElse(null);
    }

    /**
     * 最大值
     *
     * @param numList
     * @return
     */
    public static BigDecimal max(Collection<BigDecimal> numList) {
        return numList.stream().filter(Objects::nonNull).max(BigDecimal::compareTo).orElse(null);
    }

    /**
     * 平均值
     *
     * @param numList
     * @return
     */
    public static BigDecimal avg(Collection<BigDecimal> numList) {
        return avg(numList, DEFAULT_SCALE);
    }

    /**
     * 平均值
     *
     * @param numList
     * @param scale
     * @return
     */
    public static BigDecimal avg(Collection<BigDecimal> numList, int scale) {
        return avg(numList, scale, RoundingMode.HALF_UP);
    }

    /**
     * 平均值
     *
     * @param numList
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal avg(Collection<BigDecimal> numList, int scale, RoundingMode roundingMode) {
        numList = numList.stream().filter(Objects::nonNull).collect(Collectors.toList());
        int size = numList.size();
        if (size == 0) return null;
        return divide(sum(numList), BigDecimal.valueOf(size), scale, roundingMode);
    }

    /**
     * 获取小数部分
     *
     * @param num
     * @return
     */
    public BigDecimal getFraction(BigDecimal num) {
        return num.remainder(BigDecimal.ONE);
    }

    /**
     * 转字符串，去小数多余零，去科学计数法
     *
     * @param num
     * @return
     */
    public String toString(BigDecimal num) {
        if (num == null) return null;
        return num.stripTrailingZeros().toPlainString();
    }

}
