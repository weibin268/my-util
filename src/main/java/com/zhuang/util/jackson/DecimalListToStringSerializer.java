package com.zhuang.util.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * BigDecimalList转字符串，在对应的实体字段添加注解：@JsonSerialize(using = DecimalListToStringSerializer.class)
 */
public class DecimalListToStringSerializer extends JsonSerializer<List<BigDecimal>> {

    @Override
    public void serialize(List<BigDecimal> bigDecimalList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (BigDecimal bigDecimal : bigDecimalList) {
            jsonGenerator.writeString(toString(bigDecimal));
        }
        jsonGenerator.writeEndArray();
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
