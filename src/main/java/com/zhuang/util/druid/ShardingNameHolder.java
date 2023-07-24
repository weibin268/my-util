package com.zhuang.util.druid;

import java.util.HashMap;
import java.util.Map;

public class ShardingNameHolder {

    private static ThreadLocal<Map<String, String>> shardingNameMap = new ThreadLocal<>();

    public static String getShardingName(String tableName) {
        Map<String, String> map = shardingNameMap.get();
        if (map == null) return null;
        return map.get(tableName);
    }

    public static void setShardingName(String tableName, String shardingName) {
        Map<String, String> map = shardingNameMap.get();
        if (map == null) {
            map = new HashMap<>();
            shardingNameMap.set(map);
        }
        map.put(tableName, shardingName);
    }

    public static void removeShardingName(String tableName) {
        Map<String, String> map = shardingNameMap.get();
        if (map != null && map.containsKey(tableName)) {
            map.remove(tableName);
        }
    }

}
