package com.zhuang.util;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

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

    public static IPLocation getLocationByIp(String ip) {
        try {
            IPLocation result = new IPLocation();
            String s = HttpUtil.get("https://qifu-api.baidubce.com/ip/geo/v1/district?ip=" + ip);
            JSONObject jsonObject = JSON.parseObject(s);
            JSONObject data = jsonObject.getJSONObject("data");
            if (data != null) {
                result.setIsp(data.get("isp") != null ? data.get("isp").toString() : null);
                result.setCountry(data.get("country") != null ? data.get("country").toString() : null);
                result.setProv(data.get("prov") != null ? data.get("prov").toString() : null);
                result.setCity(data.get("city") != null ? data.get("city").toString() : null);
                result.setDistrict(data.get("district") != null ? data.get("district").toString() : null);
            }
            return result;
        } catch (Exception ex) {
            return null;
        }
    }

    @Data
    public static class IPLocation {
        // 网络运营商
        private String isp;
        // 国家
        private String country;
        // 省份
        private String prov;
        // 城市
        private String city;
        // 区域
        private String district;
    }

}
