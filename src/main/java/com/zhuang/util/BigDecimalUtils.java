package com.zhuang.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {

    public static BigDecimal divide(BigDecimal a, BigDecimal b) {

        return divide(a, b, 4, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode roundingMode) {
        if (a == null || b == null) return null;
        if (b.compareTo(BigDecimal.ZERO) == 0) return null;
        return a.divide(b, scale, roundingMode);
    }

}
