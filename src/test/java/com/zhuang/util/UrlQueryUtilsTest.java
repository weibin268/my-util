package com.zhuang.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class UrlQueryUtilsTest {

    @Test
    public void getQueryString() {
        Map<String, Object> params = new HashMap<>();
        params.put("a", 123);
        params.put("b", "abc");
        System.out.println(UrlQueryUtils.getQueryString(params));
    }

    @Test
    public void getQueryMap() {
        Map<CharSequence, CharSequence> queryMap = UrlQueryUtils.getQueryMap("name=庄伟斌&age=18");
        System.out.println(queryMap);
    }
}
