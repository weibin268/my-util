package com.zhuang.util.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class FastjsonUtils {

    public static <T> T toBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public static String toJsonStr(Object object) {
        return JSON.toJSONString(object);
    }

    public static String toJsonStr(Object object, SerializerFeature... features) {
        return JSON.toJSONString(object, features);
    }

}
