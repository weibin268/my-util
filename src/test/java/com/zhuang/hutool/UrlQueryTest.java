package com.zhuang.hutool;

import cn.hutool.core.net.url.UrlQuery;
import org.junit.Test;

import java.util.HashMap;

public class UrlQueryTest {

    @Test
    public void test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", 123);
        map.put("b", 456);
        UrlQuery urlQuery = new UrlQuery(map);
        System.out.println(urlQuery);
    }
}
