package com.zhuang.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * BigDecimal转字符串，在对应的实体字段添加注解：@JsonSerialize(using = DecimalToStringSerializer.class)
 */
public class DecimalToStringSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal bigDecimal, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(toString(bigDecimal));
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
