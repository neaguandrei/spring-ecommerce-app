package com.fmi.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "security")
public class SecurityConfigurationProperties {

    private boolean enabled;

    private String userUrl;

    private String jwtSecret;

    private Long jwtExpiration;
}
