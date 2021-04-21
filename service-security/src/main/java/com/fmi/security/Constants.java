package com.fmi.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String AUTHORIZATION = "Authorization";

    public static final String CLAIMS_USER_ID = "userId";

    public static final String CLAIMS_AUTHORITIES = "authorities";

}
