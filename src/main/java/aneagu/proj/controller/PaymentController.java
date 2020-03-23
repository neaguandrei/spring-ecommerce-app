package aneagu.proj.controller;


import aneagu.proj.models.dto.PaymentDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(value = "/add")
    public ResponseEntity<?> savePayment(@Valid @RequestBody PaymentDto paymentDto) {
        paymentService.save(paymentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(paymentService.get(id));
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsForUserId(@PathVariable Long userId) throws NotFoundException {
        return ResponseEntity.ok(paymentService.getPaymentsForUserId(userId));
    }
}
