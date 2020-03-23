package aneagu.proj.controller;

import aneagu.proj.controller.utils.ControllerUtils;
import aneagu.proj.models.dto.CustomerDto;
import aneagu.proj.models.exception.BadRequestException;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(customerService.get(id));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveCustomer(@Valid @RequestBody CustomerDto customerDto) throws BadRequestException {
        return ResponseEntity
                .created(ControllerUtils.generateUri(customerService.save(customerDto), "getCustomer",
                        CustomerController.class).toUri())
                .build();
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto customerDto) throws NotFoundException {
        customerService.update(id, customerDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws NotFoundException {
        customerService.delete(id);
        return ResponseEntity.ok().build();
    }


}
