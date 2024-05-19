package com.zhuang.util.cache;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Component
public class CacheUtils {

    private static CacheUtils _this;
    private static final String DEFAULT_CACHE_TYPE = "memory";
    @Autowired
    private List<Cacheable> cacheableList;
    @Value("${my.util.cacheType:" + DEFAULT_CACHE_TYPE + "}")
    private String cacheType;

    @PostConstruct
    private void init() {
        _this = this;
    }

    public Cacheable getCacheable() {
        return cacheableList.stream().filter(c -> c.getType().equalsIgnoreCase(cacheType)).findFirst().orElse(null);
    }

    public static void set(String key, String value, int timeoutSeconds) {
        _this.getCacheable().set(key, value, timeoutSeconds);
    }

    public static String get(String key) {
        return _this.getCacheable().get(key);
    }

    public static void setObject(String key, Object value, int timeoutSeconds) {
        String jsonValue;
        if (value == null) {
            jsonValue = null;
        } else if (ClassUtil.isPrimitiveWrapper(value.getClass())) {
            jsonValue = value.toString();
        } else if (value instanceof Date) {
            jsonValue = DateUtil.formatDateTime((Date) value);
        } else {
            jsonValue = JSONUtil.toJsonStr(value);
        }
        set(key, jsonValue, timeoutSeconds);
    }

    public static <T> T getObject(String key, Class<T> clazz) {
        String s = get(key);
        if (s == null) {
            return null;
        } else if (ClassUtil.isPrimitiveWrapper(clazz)) {
            return ReflectUtil.newInstance(clazz, s);
        } else if (clazz == Date.class) {
            return (T) DateUtil.parseDateTime(s);
        } else {
            return JSONUtil.toBean(s, clazz);
        }
    }

    public static void delete(String key) {
        _this.getCacheable().delete(key);
    }

}
