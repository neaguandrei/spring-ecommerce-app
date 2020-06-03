package aneagu.proj.controller;

import aneagu.proj.controller.utils.ControllerUtils;
import aneagu.proj.models.dto.UserDto;
import aneagu.proj.models.exception.BadRequestException;
import aneagu.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final PasswordEncoder passwordEncoder;

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated(UserDto.SignUp.class) @RequestBody UserDto user) throws BadRequestException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity
                .created(ControllerUtils.generateUri(userService.save(user), "getCustomer",
                        CustomerController.class).toUri())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Validated(UserDto.SignIn.class) @RequestBody UserDto user) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
