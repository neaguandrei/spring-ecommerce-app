package com.fmi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fmi.security.config.SecurityConfigurationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityConfigurationProperties securityConfigurationProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SecurityConfigurationProperties securityConfigurationProperties) {
        super(authenticationManager);
        this.securityConfigurationProperties = securityConfigurationProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(Constants.AUTHORIZATION);

        if (header == null || !header.startsWith(Constants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION);
        if (token == null) {
            return null;
        }

        String user = JWT.require(Algorithm.HMAC512(securityConfigurationProperties.getJwtSecret().getBytes()))
                .build()
                .verify(token.replace(Constants.TOKEN_PREFIX, ""))
                .getSubject();
        if (user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}
