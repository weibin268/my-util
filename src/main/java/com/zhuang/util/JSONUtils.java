package com.zhuang.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.*;

public class JSONUtils {

    public static List<DiffInfo> getJsonDiff(String json1, String json2) {
        List<DiffInfo> diffInfoList = new ArrayList<>();
        Map map1 = null, map2 = null;
        map1 = JSON.parseObject(json1, Map.class);
        map2 = JSON.parseObject(json2, Map.class);
        if (map1 != null && map2 != null) {
            resolvingMapDiff(map1, map2, "", diffInfoList);
        }
        return diffInfoList;
    }

    public static void resolvingMapDiff(Map map1, Map map2, String parent, List<DiffInfo> diffInfoList) {
        Set allKey = new LinkedHashSet();
        allKey.addAll(map1.keySet());
        map2.keySet().forEach(item -> {
            if (!allKey.contains(item)) {
                allKey.add(item);
            }
        });
        allKey.forEach(k -> {
            if (!map2.containsKey(k)) {
                DiffInfo diffInfo = new DiffInfo();
                diffInfoList.add(diffInfo);
                diffInfo.setType("D");
                diffInfo.setKey(getKeyString(parent, k));
                diffInfo.setOldValue(map1.get(k));
                return;
            }
            if (!map1.containsKey(k)) {
                DiffInfo diffInfo = new DiffInfo();
                diffInfoList.add(diffInfo);
                diffInfo.setType("A");
                diffInfo.setKey(getKeyString(parent, k));
                diffInfo.setNewValue(map2.get(k));
                return;
            }
            Object v = map1.get(k);
            if (v instanceof JSONObject) {
                resolvingMapDiff(JSON.parseObject(JSON.toJSONString(v), Map.class), JSON.parseObject(JSON.toJSONString(map2.get(k)), Map.class), getKeyString(parent, k), diffInfoList);
            } else if (v instanceof JSONArray) {
                JSONArray object1 = sortJsonArray((JSONArray) v);
                JSONArray object2 = sortJsonArray(JSON.parseArray(JSON.toJSONString(map2.get(k))));
                for (int i = 0; i < object1.size(); i++) {
                    HashMap<String, Object> hs1 = new HashMap<>();
                    HashMap<String, Object> hs2 = new HashMap<>();
                    hs1.put(String.valueOf(i), object1.get(i));
                    hs2.put(String.valueOf(i), object2.get(i));
                    resolvingMapDiff(hs1, hs2, getKeyString(parent, k), diffInfoList);
                }
            } else {
                if (!v.equals(map2.get(k))) {
                    DiffInfo diffInfo = new DiffInfo();
                    diffInfoList.add(diffInfo);
                    diffInfo.setType("U");
                    diffInfo.setKey(getKeyString(parent, k));
                    diffInfo.setOldValue(map1.get(k));
                    diffInfo.setNewValue(map2.get(k));
                }
            }
        });
    }

    public static JSONArray sortJsonArray(JSONArray array) {
        List<Object> list = array.toJavaList(Object.class);
        list.sort(Comparator.comparing(Object::toString));
        return JSON.parseArray(JSON.toJSONString(list));
    }

    public static String getKeyString(String parent, Object key) {
        if (StrUtil.isEmpty(parent)) return key.toString();
        return parent + "." + key;
    }

    @Data
    public static class DiffInfo {
        private String type;
        private String key;
        private Object oldValue;
        private Object newValue;
    }
}
