package com.utopia.pmc.utils;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utopia.pmc.data.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
    @Autowired
    private EnvironmentVariable environmentVariable;

    private String doGenerateToken(Map<String, Object> claims, String subject, Integer expriesTime) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + environmentVariable.getExpiresTime() * expriesTime))
                .signWith(SignatureAlgorithm.HS512, environmentVariable.getJwtSecret()).compact();
    }

    public String generateJwtToken(User user, Integer expiresTime) {
        Map<String, Object> claims = new Hashtable();
        claims.put("username", user.getEmail());
        claims.put("role", user.getRole());
        claims.put("phone", user.getPhone());
        return doGenerateToken(claims, user.getEmail(), expiresTime);
    }

    public Jws<Claims> getJwsClaims(String token) {
        return Jwts.parser().setSigningKey(environmentVariable.getJwtSecret()).parseClaimsJws(token);
    }

    public String getUsernameFromClaims(Claims claims) {
        return claims.get("username").toString();
    }

    public String getPhoneFromClaims(Claims claims) {
        return claims.get("phone").toString();
    }
}
