package com.zhuang.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlUtils {

    public static String fillSqlParams(String sql, Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            Pattern pattern = Pattern.compile("#\\{(?<g0>.+?)\\}");
            Matcher matcher = pattern.matcher(sql);
            StringBuffer sbResult = new StringBuffer();
            while (matcher.find()) {
                String content = matcher.group("g0");
                content = StrUtil.trim(content);
                Object o = params.get(content);
                String str = "";
                if (o != null) {
                    if (o instanceof String) {
                        str = StrUtil.format("'{}'", o.toString());
                    } else if (o instanceof Date) {
                        str = StrUtil.format("'{}'", DateUtil.formatDateTime((Date) o));
                    } else {
                        str = o.toString();
                    }
                }
                matcher.appendReplacement(sbResult, str);
            }
            if (sbResult.length() > 0) {
                matcher.appendTail(sbResult);
                sql = sbResult.toString();
            }

        }
        return sql;
    }

}
