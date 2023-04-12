package com.zhuang.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.net.url.UrlQuery;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UrlQueryUtils {

    public static String getQueryString(Object obj) {
        if (obj instanceof Map) {
            return UrlQuery.of((Map<? extends CharSequence, ?>) obj).toString();
        } else {
            return UrlQuery.of(BeanUtil.beanToMap(obj)).toString();
        }
    }

    public static Map<CharSequence, CharSequence> getQueryMap(String queryString) {
        Map<CharSequence, CharSequence> queryMap = UrlQuery.of(queryString, StandardCharsets.UTF_8).getQueryMap();
        return queryMap;
    }
}
