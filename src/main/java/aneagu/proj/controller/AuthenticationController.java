package aneagu.proj.controller;

import aneagu.proj.controller.utils.ControllerUtils;
import aneagu.proj.models.dto.CustomerDto;
import aneagu.proj.models.exception.BadRequestException;
import aneagu.proj.service.CustomerService;
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

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> saveCustomer(@Validated(CustomerDto.SignUp.class) @RequestBody CustomerDto user) throws BadRequestException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity
                .created(ControllerUtils.generateUri(customerService.save(user), "getCustomer",
                        CustomerController.class).toUri())
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@Validated(CustomerDto.SignIn.class) @RequestBody CustomerDto user) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
