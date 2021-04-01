package com.fmi.user.controller;

import com.fmi.common.exception.NotFoundException;
import com.fmi.user.dto.UserDto;
import com.fmi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) throws NotFoundException {
        return ResponseEntity.ok(userService.getById(userId));
    }

    @PutMapping(value = "/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @Validated(UserDto.SignUp.class) @RequestBody UserDto userDto) {
        userService.update(userId, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/remove/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) throws NotFoundException {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }
}
