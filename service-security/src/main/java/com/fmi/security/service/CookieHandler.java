package com.fmi.security.service;

import com.fmi.security.config.SecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

import static com.fmi.security.SecurityConstants.REFRESH_TOKEN_COOKIE_NAME;
import static com.fmi.security.SecurityConstants.REFRESH_TOKEN_COOKIE_PATH;

@Component
@RequiredArgsConstructor
public class CookieHandler {

    private final SecurityConfigurationProperties securityConfigurationProperties;

    public Cookie create(String refreshToken) {
        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_COOKIE_NAME, refreshToken);
        refreshTokenCookie.setMaxAge(securityConfigurationProperties.getRefreshExpiration().intValue());
        refreshTokenCookie.setPath(REFRESH_TOKEN_COOKIE_PATH);

        return refreshTokenCookie;
    }
}