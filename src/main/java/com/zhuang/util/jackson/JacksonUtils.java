package com.zhuang.util.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            // 设置发现未知字段不报错
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 允许解析NoN等无效值（注：新json标准不允许出现这样的值，所以该特性后续版本会弃用）
            //objectMapper.configure(ALLOW_NON_NUMERIC_NUMBERS, true);
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJsonStr(Object object) {
        try {
            // 设置字段为null的不进行序列化
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
