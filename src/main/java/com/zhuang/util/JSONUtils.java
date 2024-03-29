package com.zhuang.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.*;

public class JSONUtils {

    public static List<DiffInfo> getJsonDiffList(String json1, String json2) {
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
            Object v1 = map1.get(k);
            Object v2 = map2.get(k);
            if (v1 instanceof JSONObject) {
                resolvingMapDiff(JSON.parseObject(JSON.toJSONString(v1), Map.class), JSON.parseObject(JSON.toJSONString(map2.get(k)), Map.class), getKeyString(parent, k), diffInfoList);
            } else if (v1 instanceof JSONArray) {
                JSONArray object1 = (JSONArray) v1;
                JSONArray object2 = (JSONArray) v2;
                int size = object2.size() > object1.size() ? object2.size() : object1.size();
                for (int i = 0; i < size; i++) {
                    HashMap<String, Object> hs1 = new HashMap<>();
                    HashMap<String, Object> hs2 = new HashMap<>();
                    Object row1 = object1.size() >= size ? object1.get(i) : new HashMap<>();
                    Object row2 = object2.size() >= size ? object2.get(i) : new HashMap<>();
                    hs1.put(String.valueOf(i), row1);
                    hs2.put(String.valueOf(i), row2);
                    resolvingMapDiff(hs1, hs2, getKeyString(parent, k), diffInfoList);
                }
            } else {
                if (!v1.equals(map2.get(k))) {
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
