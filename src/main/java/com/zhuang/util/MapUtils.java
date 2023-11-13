package com.zhuang.util;

import cn.hutool.core.util.StrUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapUtils {

    public static void removeNullOrEmpty(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        Set<String> keySet4Remove = new HashSet<>();
        for (String key : keySet) {
            Object objItem = map.get(key);
            if (objItem == null) {
                keySet4Remove.add(key);
            }
            if (objItem instanceof String) {
                if (StrUtil.isEmpty((String) objItem)) {
                    keySet4Remove.add(key);
                }
            }
        }
        for (String s : keySet4Remove) {
            map.remove(s);
        }
    }
}
