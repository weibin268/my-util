package com.zhuang.util;

import io.jsonwebtoken.Claims;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JwtUtilsTest {

    @Test
    public void createToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "abc");
        claims.put("userName", "zwb");
        String token = JwtUtils.createToken(claims, 100000L);
        System.out.println(token);
    }

    @Test
    public void parseToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyTmFtZSI6Inp3YiIsImV4cCI6MTY2MTg3MTQ2MywidXNlcklkIjoiYWJjIn0.2CZxVYU2r8XfqjtOlHAPx6HYxW2vpf1j1SCKqrRnKndbF29ins6MVM4dy8KHUt6FmpbJjvavDHvO66Lgjz44HQ";
        Claims claims = JwtUtils.parseToken(token);
        System.out.println(claims);
    }

}