package com.zhuang.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    private String name;
    private Integer age;
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
}
