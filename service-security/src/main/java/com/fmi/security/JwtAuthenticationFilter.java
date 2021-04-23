package com.fmi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.common.exception.AuthenticationFailedException;
import com.fmi.security.config.SecurityConfigurationProperties;
import com.fmi.security.model.MyUserDetails;
import com.fmi.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.fmi.security.Constants.CLAIMS_AUTHORITIES;
import static com.fmi.security.Constants.CLAIMS_USER_ID;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final SecurityConfigurationProperties securityConfigurationProperties;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            final MyUserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            final Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDetails.getUser(),
                            user.getPassword(),
                            authorities)
            );
        } catch (IOException e) {
            throw new AuthenticationFailedException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        final String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getEmail())
                .withClaim(CLAIMS_USER_ID, ((User) authResult.getPrincipal()).getId())
                .withArrayClaim(CLAIMS_AUTHORITIES, toArrayNames(authResult.getAuthorities()))
                .withExpiresAt(new Date(System.currentTimeMillis() + securityConfigurationProperties.getJwtExpiration()))
                .sign(Algorithm.HMAC512(securityConfigurationProperties.getJwtSecret().getBytes()));
        response.addHeader(Constants.AUTHORIZATION, Constants.TOKEN_PREFIX + token);
    }

    private String[] toArrayNames(Collection<? extends GrantedAuthority> authorities) {
        final String[] result = new String[authorities.size()];
        authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toArray(result);
        return result;
    }
}
