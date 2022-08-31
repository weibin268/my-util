package com.zhuang.util;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
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
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyTmFtZSI6Inp3YiIsImV4cCI6MTY2MTkxNDUzMywidXNlcklkIjoiYWJjIn0.ghZC_MuL-AN_WLqZr7o_HMV04SZc46zOc9IF-XPV5r-SSnmAvV9iWZYIAUsYNi6YxHe6MRBvcKqbq_uGnqdzhA";
        try {
            Claims claims = JwtUtils.parseToken(token);
            System.out.println(claims);
        } catch (SignatureException signatureException) {
            System.out.println("token 签名异常");
        } catch (ExpiredJwtException expiredJwtException) {
            System.out.println("token 过期！");
        }
    }

}
