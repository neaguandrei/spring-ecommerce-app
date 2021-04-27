package com.fmi.security.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ConfigurationProperties(prefix = "security")
public class SecurityConfigurationProperties {

    private boolean enabled;

    private String userUrl;

    private String refreshTokenUrl;

    private String jwtSecret;

    private Long jwtExpiration;

    private Long refreshExpiration;

}
