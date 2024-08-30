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

    public static String trimLeft(String str, char... ccc) {
        return trim(str, -1, ccc);
    }

    public static String trimRight(String str, char... ccc) {
        return trim(str, 1, ccc);
    }

    public static String trim(String str, char... ccc) {
        return trim(str, 0, ccc);
    }

    public static String trim(String str, int pos, char... ccc) {
        int startIndex = 0;
        int endIndex = str.length() - 1;
        if (pos <= 0) {
            for (int i = 0; i < str.length(); i++) {
                if (!charInChars(str.charAt(i), ccc)) {
                    startIndex = i;
                    break;
                }
            }
        }
        if (pos >= 0) {
            endIndex = -1;
            for (int i = str.length() - 1; i >= 0; i--) {
                if (!charInChars(str.charAt(i), ccc)) {
                    endIndex = i;
                    break;
                }
            }
        }
        return str.substring(startIndex, endIndex + 1);
    }

    public static boolean charInChars(char c, char... ccc) {
        boolean result = false;
        for (char c2 : ccc) {
            if (c == c2) {
                result = true;
                break;
            }
        }
        return result;
    }

}
