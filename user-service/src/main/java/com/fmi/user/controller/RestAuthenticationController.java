package com.fmi.user.controller;

import com.fmi.api.user.UserDto;
import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.common.exception.TokenRevokedException;
import com.fmi.security.SecurityConstants;
import com.fmi.security.model.RefreshTokenResource;
import com.fmi.security.model.UserPrincipal;
import com.fmi.security.service.CookieHandler;
import com.fmi.user.mapper.UserMapper;
import com.fmi.user.model.User;
import com.fmi.user.service.RefreshTokenService;
import com.fmi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.fmi.security.SecurityConstants.REFRESH_TOKEN_COOKIE_NAME;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RestAuthenticationController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final RefreshTokenService refreshTokenService;

    private final CookieHandler cookieHandler;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Validated(UserDto.SignUp.class) @RequestBody UserDto user) throws BadRequestException, NotFoundException {
        userService.save(userMapper.mapFromDto(user));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Validated(UserDto.SignIn.class) @RequestBody UserDto user) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable(value = "email") String email) throws NotFoundException {
        return ResponseEntity.ok(userMapper.mapToDto(userService.getByEmail(email)));
    }

    @PostMapping(value = "/refresh-token/{email}")
    public ResponseEntity<RefreshTokenResource> createRefreshToken(@PathVariable(value = "email") String email) throws NotFoundException {
        return ResponseEntity.ok((new RefreshTokenResource(refreshTokenService.create(email))));
    }

    @PostMapping(value = "/refresh-token")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> refresh(@CookieValue(REFRESH_TOKEN_COOKIE_NAME) String refreshToken,
                                          HttpServletResponse response) throws NotFoundException, TokenRevokedException {
        RefreshTokenResource model = refreshTokenService.createAccessToken(refreshToken);
        response.addCookie(cookieHandler.create(model.getRefreshToken()));
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.TOKEN_PREFIX + model.getAccessToken());

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/refresh-token/cancellation")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> revoke(@CookieValue(REFRESH_TOKEN_COOKIE_NAME) String refreshToken,
                                         Authentication authentication) throws NotFoundException, TokenRevokedException {
        User user = userService.getByEmail(((UserPrincipal) authentication.getPrincipal()).getEmail());
        refreshTokenService.revoke(user.getId(), refreshToken);

        return ResponseEntity.noContent().build();
    }
}
