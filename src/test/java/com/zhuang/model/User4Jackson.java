package com.zhuang.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhuang.util.jackson.DecimalToStringSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class User4Jackson {

    private String name;
    private Integer age;
    @JsonSerialize(using = DecimalToStringSerializer.class)
    private BigDecimal height;
    private Date birthday;

}
