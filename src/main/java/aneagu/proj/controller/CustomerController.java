package aneagu.proj.controller;

import aneagu.proj.models.dto.CustomerDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(customerService.get(id));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Validated(CustomerDto.SignUp.class) @RequestBody CustomerDto customerDto) throws NotFoundException {
        customerService.update(id, customerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws NotFoundException {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }
}
