package com.fmi.security.service;

import com.fmi.security.config.SecurityConfigurationProperties;
import com.fmi.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserGatewayService {

    private final RestTemplate restTemplate;

    private final SecurityConfigurationProperties securityConfigurationProperties;

    public User getUserByEmail(String email) {
        final ResponseEntity<User> responseEntity = restTemplate.getForEntity(securityConfigurationProperties.getUserUrl(), User.class, email);
        return responseEntity.getBody();
    }
}
