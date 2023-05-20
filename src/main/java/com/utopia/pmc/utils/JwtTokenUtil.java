package com.utopia.pmc.utils;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.utopia.pmc.data.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtil {
    @Value("${jwt.secret-key}")
    private String jwtSecret;
    @Value("${jwt.expires-time}")
    private long expiresTime;

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiresTime * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String generateJwtToken(User user) {
        Map<String, Object> claims = new Hashtable();
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole());
        claims.put("phone", user.getPhone());
        return doGenerateToken(claims, user.getUsername());
    }

    public Jws<Claims> getJwsClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }

    public String getUsernameFromClaims(Claims claims) {
        return claims.get("username").toString();
    }

    public String getPhoneFromClaims(Claims claims) {
        return claims.get("phone").toString();
    }
}