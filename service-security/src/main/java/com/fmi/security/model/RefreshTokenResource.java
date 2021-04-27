package com.fmi.security.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class RefreshTokenResource {

    private String refreshToken;

    private String accessToken;

    public RefreshTokenResource(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshTokenResource(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
