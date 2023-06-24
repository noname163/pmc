package com.utopia.pmc.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class EnvironmentVariable {
    @Value("${jwt.secret-key}")
    private String jwtSecret;
    @Value("${jwt.expires-time}")
    private long expiresTime;
    @Value("${app.base-url}")
    private String baseurl;
}
