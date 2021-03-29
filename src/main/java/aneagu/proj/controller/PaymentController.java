package aneagu.proj.controller;


import aneagu.proj.models.dto.PaymentDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.PaymentService;
import aneagu.proj.service.ResourceAssemblerService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    private final ResourceAssemblerService resourceAssembler;

    @PostMapping(value = "/add")
    public ResponseEntity<?> savePayment(@Valid @RequestBody PaymentDto paymentDto) {
        paymentService.save(paymentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(paymentService.get(id));
    }

    @GetMapping(value = "/orders/{orderId}", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PaymentDto>> getPaymentsForOrder(@PathVariable Long orderId) throws NotFoundException {
        return ResponseEntity.ok(resourceAssembler.assemblePaymentsResource(paymentService.getPaymentsForOrderId(orderId)));
    }
}
