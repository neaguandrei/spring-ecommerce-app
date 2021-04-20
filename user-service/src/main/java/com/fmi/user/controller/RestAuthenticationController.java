package com.fmi.user.controller;

import com.fmi.api.user.UserDto;
import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.user.mapper.UserMapper;
import com.fmi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RestAuthenticationController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Validated(UserDto.SignUp.class) @RequestBody UserDto user) throws BadRequestException {
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
}
