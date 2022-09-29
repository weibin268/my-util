package com.zhuang.util.spring.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    public String get(String key) {
        return _this.getCacheable().get(key);
    }

    public void delete(String key) {
        _this.getCacheable().delete(key);
    }

}
