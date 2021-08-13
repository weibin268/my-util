package com.zhuang.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpClientUtilsTest {

    private static final String appKey = "";
    private static final String appSecret = "";
    private static final String accessToken="";

    @Test
    public void token() {
        Map params = new HashMap();
        params.put("appKey", appKey);
        params.put("appSecret", appSecret);
        String s = HttpClientUtils.doPost("https://open.ys7.com/api/lapp/token/get", params);
        System.out.println(s);
    }

    @Test
    public void cameraList() {
        Map params = new HashMap();
        params.put("accessToken", accessToken);
        params.put("pageStart", 0);
        params.put("pageSize", 10);
        String s = HttpClientUtils.doPost("https://open.ys7.com/api/lapp/camera/list", params);
        System.out.println(s);
    }

    @Test
    public void deviceList() {
        Map params = new HashMap();
        params.put("accessToken", accessToken);
        params.put("pageStart", 0);
        params.put("pageSize", 10);
        String s = HttpClientUtils.doPost("https://open.ys7.com/api/lapp/device/list", params);
        System.out.println(s);
    }

    @Test
    public void liveAddress() {
        Map params = new HashMap();
        params.put("accessToken", accessToken);
        params.put("deviceSerial", "F74120272");
        params.put("channelNo", 1);
        params.put("protocol",4);
        String s = HttpClientUtils.doPost("https://open.ys7.com/api/lapp/v2/live/address/get", params);
        System.out.println(s);
    }

}
