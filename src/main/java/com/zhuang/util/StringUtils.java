package com.zhuang.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

}
