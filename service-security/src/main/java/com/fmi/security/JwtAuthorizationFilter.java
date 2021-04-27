package com.fmi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fmi.security.config.SecurityConfigurationProperties;
import com.fmi.security.model.UserPrincipal;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fmi.security.SecurityConstants.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final SecurityConfigurationProperties securityConfigurationProperties;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, SecurityConfigurationProperties securityConfigurationProperties) {
        super(authenticationManager);
        this.securityConfigurationProperties = securityConfigurationProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.AUTHORIZATION);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        final UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.AUTHORIZATION);
        if (token == null) {
            return null;
        }

        final DecodedJWT decodedJWT = JWT
                .require(Algorithm.HMAC512(securityConfigurationProperties.getJwtSecret().getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""));

        final String email = decodedJWT.getSubject();
        final Long id = decodedJWT.getClaim(CLAIMS_USER_ID).asLong();
        if (email == null || id == null) {
            return null;
        }

        final String[] decodedAuthorities = decodedJWT.getClaim(CLAIMS_AUTHORITIES).asArray(String.class);
        List<? extends GrantedAuthority> authorities = Arrays.stream(decodedAuthorities).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        final UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(id)
                .email(email)
                .build();

        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }
}
