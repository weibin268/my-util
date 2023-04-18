package com.zhuang.util.security;

import org.junit.Test;

public class AesUtilsTest {
    
    @Test
    public void test() {
        String abc = AesUtils.encryptStr("abc");
        System.out.println(abc);
        System.out.println(AesUtils.decryptStr(abc));
    }

    @Test
    public void getKey() {
        System.out.println(AesUtils.getKey());
    }

}
