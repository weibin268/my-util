package com.zhuang.util.fastjson;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;

public class FastjsonUtils {
    
    public static <T> T toBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static String toJsonStr(Object object) {
        return JSON.toJSONString(object);
    }

}
