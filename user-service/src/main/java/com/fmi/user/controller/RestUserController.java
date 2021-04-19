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

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    private final UserMapper userMapper;


    @PutMapping(value = "/{user_id}")
    public ResponseEntity<Object> updateUser(@PathVariable("user_id") Long userId, @Validated(UserDto.SignUp.class) @NotNull @RequestBody UserDto userDto) throws BadRequestException {
        userService.update(userId, userMapper.mapFromDto(userDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{user_id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("user_id") Long userId) throws NotFoundException {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }
}
