package com.fmi.security;

import com.fmi.security.config.SecurityConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    private final SecurityConfigurationProperties securityConfigurationProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String email = authentication.getPrincipal().toString();
        final String password = authentication.getCredentials().toString();

        try {
            final ResponseEntity<UserDto> responseEntity = restTemplate.getForEntity(securityConfigurationProperties.getUserUrl(),
                    UserDto.class, email);
            final UserDto userDto = responseEntity.getBody();

            if (Objects.isNull(userDto) || !passwordEncoder.matches(password, userDto.getPassword())) {
                throw new BadCredentialsException("Passwords don't match!");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new BadCredentialsException(String.format("E-mail: %s doesn't exist!", email));
        }

        return new UsernamePasswordAuthenticationToken(email, passwordEncoder.encode(password), Collections.emptyList());
    }
}
