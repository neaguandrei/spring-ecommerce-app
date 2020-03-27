package aneagu.proj.controller;

import aneagu.proj.models.dto.UserDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class CustomerController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getCustomer(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Validated(UserDto.SignUp.class) @RequestBody UserDto userDto) throws NotFoundException {
        userService.update(id, userDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws NotFoundException {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
