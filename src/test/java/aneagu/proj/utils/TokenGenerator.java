package aneagu.proj.utils;

import com.auth0.jwt.JWT;
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Permission;
import java.util.Date;

import static aneagu.proj.security.Constants.EXPIRATION_TIME;
import static aneagu.proj.security.Constants.SECRET;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class TokenGenerator {
    private static final String SUBJECT = "test@gmail.com";

    public static String createAuthorisationToken() {
        return "Bearer " + JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
    }
}