package com.fmi.user.controller;

import com.fmi.api.user.UserDto;
import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.user.mapper.UserMapper;
import com.fmi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PreAuthorize("#email == authentication.name")
    @PutMapping(value = "/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable("email") String email, @Validated(UserDto.SignUp.class) @NotNull @RequestBody UserDto userDto) throws BadRequestException, NotFoundException {
        userService.update(email, userMapper.mapFromDto(userDto));
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#email == authentication.name")
    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Object> deleteUserByEmail(@PathVariable("email") String email) throws NotFoundException {
        userService.delete(email);
        return ResponseEntity.ok().build();
    }
}
