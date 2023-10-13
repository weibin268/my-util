package com.zhuang.util;

import org.junit.Test;

public class IpUtilsTest {

    @Test
    public void replaceIp() {
        String s = IpUtils.replaceIp("http://192.168.1.3:8080/api/", "127.0.0.1");
        System.out.println(s);
    }

    @Test
    public void getIpList() {
        IpUtils.getIpList("http://192.168.1.3:8080/api/ 127.0.0.1").forEach(System.out::println);
    }


    @Test
    public void getLocationByIp() {
        IpUtils.IPLocation locationByIp = IpUtils.getLocationByIp("120.238.64.59");
        System.out.println(locationByIp);
    }
}
