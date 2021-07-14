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
            jsonSerializer.write(toString((BigDecimal) o));
        }
    }

    /**
     * 转字符串，去小数多余零，去科学计数法
     *
     * @param num
     * @return
     */
    private String toString(BigDecimal num) {
        if (num == null) return null;
        return num.stripTrailingZeros().toPlainString();
    }

}
