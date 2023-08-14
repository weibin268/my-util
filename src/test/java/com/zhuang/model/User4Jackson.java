package com.zhuang.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    @JsonProperty("job_title")
    private String jobTitle;

}
