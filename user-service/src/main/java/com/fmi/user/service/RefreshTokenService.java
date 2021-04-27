package com.fmi.user.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.common.exception.TokenRevokedException;
import com.fmi.security.config.SecurityConfigurationProperties;
import com.fmi.security.model.RefreshTokenResource;
import com.fmi.security.service.JwtHandler;
import com.fmi.user.dao.entity.RefreshTokenEntity;
import com.fmi.user.dao.entity.UserEntity;
import com.fmi.user.dao.repository.RefreshTokenRepository;
import com.fmi.user.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmi.security.JwtAuthenticationFilter.toArrayNames;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final SecurityConfigurationProperties applicationProperties;

    private final UserRepository userRepository;

    private final JwtHandler jwtHandler;

    private final RefreshTokenRepository refreshTokenRepository;

    public String create(String email) throws NotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
        RefreshTokenEntity refreshToken = new RefreshTokenEntity(user, UUID.randomUUID().toString().replace("-", ""));
        refreshTokenRepository.save(refreshToken);

        return refreshToken.getToken();
    }

    public boolean validate(String refreshToken) throws NotFoundException {
        Optional<RefreshTokenEntity> optionalRefreshToken = refreshTokenRepository.findByToken(refreshToken);
        if (!optionalRefreshToken.isPresent()) {
            throw new NotFoundException("Refresh token not found.");
        }

        RefreshTokenEntity token = optionalRefreshToken.get();
        if (token.getRevoked() != null) {
            return false;
        }

        LocalDate tokenExpireDate = token.getCreated().plusSeconds(applicationProperties.getRefreshExpiration() / 60).toLocalDate();

        return tokenExpireDate.isAfter(LocalDate.now());
    }

    public RefreshTokenResource createAccessToken(String refreshToken) throws TokenRevokedException, NotFoundException {
        Optional<RefreshTokenEntity> optionalRefreshToken = refreshTokenRepository.findByToken(refreshToken);
        if (!optionalRefreshToken.isPresent()) {
            throw new NotFoundException("Refresh token not found.");
        }

        RefreshTokenEntity token = optionalRefreshToken.get();
        if (token.getRevoked() != null) {
            throw new TokenRevokedException("Token already revoked for: " + token.getToken());
        }

        UserEntity user = token.getUser();
        Collection<? extends GrantedAuthority> authoritiesCollection = Arrays.stream(new String[]{user.getRoles().toString()})
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        String accessToken = jwtHandler.create(user.getEmail(), user.getId(), toArrayNames(authoritiesCollection));
        revoke(user.getId(), token.getToken());
        String newRefreshToken = create(user.getEmail());

        return new RefreshTokenResource(newRefreshToken, accessToken);
    }

    public void revoke(Long userId, String refreshToken) throws NotFoundException, TokenRevokedException {
        Optional<RefreshTokenEntity> optionalRefreshToken = refreshTokenRepository.findByToken(refreshToken);
        if (!optionalRefreshToken.isPresent() || !optionalRefreshToken.get().getUser().getId().equals(userId)) {
            throw new NotFoundException("Refresh token not found.");
        }

        RefreshTokenEntity token = optionalRefreshToken.get();
        if (token.getRevoked() != null) {
            throw new TokenRevokedException("Token already revoked for: " + token.getToken());
        }

        token.setRevoked(LocalDateTime.now());
        refreshTokenRepository.save(token);
    }
}
