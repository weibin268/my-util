package com.zhuang.util.security;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.zhuang.util.ByteUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtils {

    private static AES aes = SecureUtil.aes(ByteUtils.toBytes("4352FE674B45754BBD7000DA30ECD6FF"));

    public static String encryptStr(String str) {
        return aes.encryptHex(str);
    }

    public static String decryptStr(String str) {
        return aes.decryptStr(str);
    }

    public static String getKey() {
        return ByteUtils.toHex(KeyUtil.generateKey("AES", 128).getEncoded());
    }


}
