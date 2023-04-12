package com.zhuang.util;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;

import java.nio.charset.StandardCharsets;

public class DesUtils {

    private static DES des = SecureUtil.des("zhuangweibin".getBytes(StandardCharsets.UTF_8));

    public static String encryptStr(String str) {
        return des.encryptBase64(str);
    }

    public static String decryptStr(String str) {
        return des.decryptStr(str);
    }
}
