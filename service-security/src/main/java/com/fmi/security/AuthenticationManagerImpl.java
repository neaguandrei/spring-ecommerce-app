package com.fmi.security;

import com.fmi.security.model.User;
import com.fmi.security.service.UserGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserGatewayService userGatewayService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = ((User) authentication.getPrincipal()).getEmail();
        final List<? extends GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        final String password = authentication.getCredentials().toString();
        try {
            final User user = userGatewayService.getUserByEmail(email);
            if (Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Passwords don't match!");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new BadCredentialsException(String.format("E-mail: %s doesn't exist!", email));
        }

        return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), passwordEncoder.encode(password), authorities);
    }
}
