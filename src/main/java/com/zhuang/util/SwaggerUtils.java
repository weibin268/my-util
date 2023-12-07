package com.zhuang.util;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

public class SwaggerUtils {

    public static Map<String, String> getPropertyNameMap(Object bean, Class<?>... propertyClasses) {
        Map result = new HashMap();
        BeanUtils.recursiveProperty(bean, propertyContext -> {
            ApiModelProperty apiModelProperty = propertyContext.getField().getAnnotation(ApiModelProperty.class);
            if (apiModelProperty != null) {
                result.put(propertyContext.getFullName(), apiModelProperty.value());
            }
        }, propertyClasses);
        return result;
    }

}
