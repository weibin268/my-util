package com.zhuang.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class User {

    private String name;
    private Integer age;
    private BigDecimal height;
    private Date birthday;
    private User wife;
    private List<User> sons = new ArrayList<>();

}
