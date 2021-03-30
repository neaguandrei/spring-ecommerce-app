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

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Validated(UserDto.SignUp.class) @RequestBody UserDto userDto) {
        userService.update(id, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) throws NotFoundException {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
