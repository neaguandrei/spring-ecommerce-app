package com.fmi.security;

import com.fmi.security.config.SecurityConfigurationProperties;
import com.fmi.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Objects;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RestTemplate restTemplate;

    private final SecurityConfigurationProperties securityConfigurationProperties;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final ResponseEntity<User> responseEntity = restTemplate.getForEntity(securityConfigurationProperties.getUserUrl(), User.class, email);
        final User user = responseEntity.getBody();
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND || Objects.isNull(user)) {
            throw new UsernameNotFoundException(email);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), emptyList());
    }
}
