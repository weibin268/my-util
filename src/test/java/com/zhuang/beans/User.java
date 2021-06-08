package com.zhuang.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    private String name;
    private Integer age;
    private BigDecimal height;
    private Date birthday;
    private User wife;
    private List<User> sons = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public User getWife() {
        return wife;
    }

    public void setWife(User wife) {
        this.wife = wife;
    }

    public List<User> getSons() {
        return sons;
    }

    public void setSons(List<User> sons) {
        this.sons = sons;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", birthday=" + birthday +
                ", wife=" + wife +
                ", sons=" + sons +
                '}';
    }
}
