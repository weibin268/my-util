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

    public static String format2(String format, Object... args) {
        StringBuilder result = new StringBuilder();
        int argIndex = 0;
        int formatIndex = 0;

        while (formatIndex < format.length()) {
            if (format.charAt(formatIndex) == '{') {
                formatIndex++;
                if (formatIndex < format.length() && format.charAt(formatIndex) == '}') {
                    // 处理空占位符 {}
                    if (argIndex < args.length) {
                        result.append(args[argIndex++]);
                    } else {
                        throw new IllegalArgumentException("Not enough arguments for format string");
                    }
                    formatIndex++;
                } else {
                    // 处理带索引的占位符 {index}
                    int endIndex = format.indexOf('}', formatIndex);
                    if (endIndex == -1) {
                        throw new IllegalArgumentException("Invalid format string");
                    }
                    String indexStr = format.substring(formatIndex, endIndex);
                    int index = Integer.parseInt(indexStr);
                    if (index < 0 || index >= args.length) {
                        throw new IllegalArgumentException("Invalid argument index in format string");
                    }
                    result.append(args[index]);
                    formatIndex = endIndex + 1;
                }
            } else {
                result.append(format.charAt(formatIndex++));
            }
        }

        if (argIndex < args.length) {
            throw new IllegalArgumentException("Too many arguments for format string");
        }

        return result.toString();
    }

}
