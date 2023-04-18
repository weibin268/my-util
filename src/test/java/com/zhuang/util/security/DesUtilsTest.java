package com.zhuang.util.security;

import org.junit.Test;

public class DesUtilsTest {

    @Test
    public void test(){
        String abc = DesUtils.encryptStr("abc");
        System.out.println(abc);
        System.out.println(DesUtils.decryptStr(abc));
    }

}
