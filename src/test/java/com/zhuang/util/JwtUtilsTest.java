package com.zhuang.util;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtilsTest {

    @Test
    public void createToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "abc");
        claims.put("userName", "zwb");
        String token = JwtUtils.createToken(claims, DateUtil.offsetMinute(new Date(), 1));
        System.out.println(token);
    }

    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyTmFtZSI6Inp3YiIsImV4cCI6MTY2MTg3MjM1MCwidXNlcklkIjoiYWJjIn0.8_Xl0fZrGZqzUajss6yfqEFxxELjkpOtH3-olQGOqEx0yf0hdh1xPnUY9w54FL8puVsmj5rYKtDCzjv1TE__5g";
        Claims claims = JwtUtils.parseToken(token);
        System.out.println(claims);
    }

}