package com.zhuang.util;

import cn.hutool.core.lang.PatternPool;

public class IpUtils {
    public static String replaceIp(String str, String newIp) {
        return PatternPool.IPV4.matcher(str).replaceAll(newIp);
    }
}
