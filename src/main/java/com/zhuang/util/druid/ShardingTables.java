package com.zhuang.util.druid;

import java.util.Arrays;

public enum ShardingTables {

    sys_user("sys_user", "系统用户");

    ShardingTables(String value, String name) {
        this.value = value;
        this.name = name;
    }

    private String value;
    private String name;

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static ShardingTables getByValue(String value) {
        return Arrays.stream(ShardingTables.values()).filter(c -> c.getValue().equals(value)).findFirst().orElse(null);
    }
}
