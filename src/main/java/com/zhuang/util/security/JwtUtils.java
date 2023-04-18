package com.zhuang.util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    public static String secret = "zhuangweibin";

    public static String createToken(Map<String, Object> claims) {
        return createToken(claims, null);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims, Date expiration) {
        JwtBuilder jwtBuilder = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret);
        if (expiration != null) {
            jwtBuilder.setExpiration(expiration);
        }
        return jwtBuilder.compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
