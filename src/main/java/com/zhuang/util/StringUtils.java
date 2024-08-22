package com.zhuang.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class StringUtils {

    public static List<String> getListByBeginAndEnd(String begin, String end) {
        if (StrUtil.isEmpty(begin) | StrUtil.isEmpty(end)) return Collections.EMPTY_LIST;
        if (begin.equals(end)) return Arrays.asList(begin);
        int lengthBegin = begin.length();
        int lengthEnd = end.length();
        int maxLength = lengthBegin > lengthEnd ? lengthBegin : lengthEnd;
        long lBegin = Long.parseLong(begin);
        long lEnd = Long.parseLong(end);
        if (lBegin > lEnd) return Collections.EMPTY_LIST;
        if (lBegin == lEnd) return Arrays.asList(begin);
        long lTemp = lBegin;
        List<String> result = new ArrayList<>();
        while (lTemp <= lEnd) {
            result.add(StrUtil.padPre(Long.valueOf(lTemp).toString(), maxLength, "0"));
            lTemp++;
        }
        return result;
    }

    public static String format(String template, Object... arguments) {
        if (StrUtil.isEmpty(template)) return template;
        if (arguments.length == 0) return template;
        if (template.contains("${")) {
            template = template.replace("${", "{");
        }
        if (template.contains("{}")) {
            return StrUtil.format(template, arguments);
        }
        Object params = arguments[0];
        Map<?, ?> mapParams;
        if (params instanceof Map) {
            mapParams = (Map<?, ?>) params;
        } else {
            mapParams = BeanUtil.beanToMap(params);
        }
        return StrUtil.format(template, mapParams);
    }

    public static String trim(String str, char c) {
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != c) {
                startIndex = i;
                break;
            }
        }
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) != c) {
                endIndex = i;
                break;
            }
        }
        return str.substring(startIndex, endIndex + 1);
    }

}
