package com.zhuang.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User4Swagger {

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("身高")
    private BigDecimal height;
    @ApiModelProperty("生日")
    private Date birthday;
    @ApiModelProperty("职业")
    private String jobTitle;
    @ApiModelProperty("妻子")
    private User4Swagger wife;
    @ApiModelProperty("儿子")
    private List<User4Swagger> sons = new ArrayList<>();
}
