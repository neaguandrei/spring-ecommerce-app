package com.fmi.user.controller;

import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.user.dto.UserDto;
import com.fmi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping(value = "/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @Validated(UserDto.SignUp.class) @NotNull @RequestBody UserDto userDto) throws BadRequestException {
        userService.update(userId, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) throws NotFoundException {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }
}
