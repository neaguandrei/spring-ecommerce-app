package com.fmi.user.controller;

import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.user.dto.UserDto;
import com.fmi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Validated(UserDto.SignUp.class) @RequestBody UserDto user) throws BadRequestException {
        userService.save(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Validated(UserDto.SignIn.class) @RequestBody UserDto user) {
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable(value = "email") String email) throws NotFoundException {
        return ResponseEntity.ok(userService.getByEmail(email));
    }
}
