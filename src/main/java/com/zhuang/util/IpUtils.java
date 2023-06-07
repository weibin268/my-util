package com.zhuang.util;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class IpUtils {

    public static String replaceIp(String str, String newIp) {
        if (StrUtil.isEmpty(str) || StrUtil.isEmpty(newIp)) return str;
        return PatternPool.IPV4.matcher(str).replaceAll(newIp);
    }

    public static List<String> getIpList(String str) {
        List<String> result = new ArrayList<>();
        if (StrUtil.isEmpty(str)) return result;
        Matcher matcher = PatternPool.IPV4.matcher(str);
        while (matcher.find()) {
            result.add(matcher.group(0));
        }
        return result;
    }

    public static String getFirstIp(String str) {
        List<String> ipList = getIpList(str);
        return ipList.size() > 0 ? ipList.get(0) : null;
    }


}
