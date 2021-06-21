package com.zhuang.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.zhuang.util.fastjson.DecimalToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class User4Fastjson {

    private String name;
    private Integer age;
    @JSONField(serializeUsing = DecimalToStringSerializer.class)
    private BigDecimal height;
    private Date birthday;

}
