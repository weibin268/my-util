package com.zhuang.util;

import org.junit.Test;

public class IpUtilsTest {

    @Test
    public void replaceIp() {
        String s = IpUtils.replaceIp("http://192.168.1.3:8080/api/", "127.0.0.1");
        System.out.println(s);
    }

}
