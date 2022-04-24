package com.zhuang.util.spring;

public interface Cacheable {
    void set(String key, String value, int timeoutSeconds);

    String get(String key);

    void delete(String key);

    String getType();
}
