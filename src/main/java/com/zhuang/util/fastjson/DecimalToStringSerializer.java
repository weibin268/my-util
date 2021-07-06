package com.zhuang.util.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * BigDecimal转字符串，在对应的实体字段添加注解：@JsonSerialize(using = DecimalToStringSerializer.class)
 */
public class DecimalToStringSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) {
        if (o instanceof BigDecimal) {
            jsonSerializer.write(o.toString());
        }
    }

}
