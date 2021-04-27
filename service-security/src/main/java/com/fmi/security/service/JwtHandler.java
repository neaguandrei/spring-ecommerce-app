package com.fmi.security.service;

import com.auth0.jwt.JWT;
import com.fmi.security.config.SecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.fmi.security.SecurityConstants.CLAIMS_AUTHORITIES;
import static com.fmi.security.SecurityConstants.CLAIMS_USER_ID;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtHandler {

    private final SecurityConfigurationProperties securityConfigurationProperties;

    public String create(String email, Long userId, String[] authorities) {
        return JWT.create()
                .withSubject(email)
                .withClaim(CLAIMS_USER_ID, userId)
                .withArrayClaim(CLAIMS_AUTHORITIES, authorities)
                .withExpiresAt(new Date(System.currentTimeMillis() + securityConfigurationProperties.getJwtExpiration()))
                .sign(HMAC512(securityConfigurationProperties.getJwtSecret().getBytes()));
    }
}