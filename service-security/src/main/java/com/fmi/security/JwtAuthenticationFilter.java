package com.fmi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.common.exception.AuthenticationFailedException;
import com.fmi.security.model.MyUserDetails;
import com.fmi.security.model.RefreshTokenResource;
import com.fmi.security.model.User;
import com.fmi.security.service.CookieHandler;
import com.fmi.security.service.JwtHandler;
import com.fmi.security.service.UserGatewayService;
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
import java.util.stream.Collectors;


@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserGatewayService userGatewayService;

    private final CookieHandler cookieHandler;

    private final JwtHandler jwtHandler;

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
        final User user = (User) authResult.getPrincipal();
        final String token = jwtHandler.create(user.getEmail(), user.getId(), toArrayNames(authResult.getAuthorities()));
        final RefreshTokenResource refreshTokenResource = userGatewayService.createRefreshToken(((User) authResult.getPrincipal()).getEmail());
        final String refreshToken = refreshTokenResource.getRefreshToken();

        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + token);
        response.addCookie(cookieHandler.create(refreshToken));
    }

    public static String[] toArrayNames(Collection<? extends GrantedAuthority> authorities) {
        final String[] result = new String[authorities.size()];
        authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toArray(result);
        return result;
    }
}
