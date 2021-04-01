package com.fmi.payment.controller;


import com.fmi.payment.dto.PaymentDto;
import com.fmi.payment.service.PaymentService;
import com.fmi.payment.service.ResourceAssemblerService;
import com.fmi.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class RestPaymentController {

    private final PaymentService paymentService;

    private final ResourceAssemblerService resourceAssembler;

    @PostMapping(value = "/add")
    public ResponseEntity<?> savePayment(@Valid @RequestBody PaymentDto paymentDto) {
        paymentService.save(paymentDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{paymentId}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long paymentId) throws NotFoundException {
        return ResponseEntity.ok(paymentService.getById(paymentId));
    }

    @GetMapping(value = "/orders/{orderId}", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PaymentDto>> getPaymentsForOrder(@PathVariable String orderId) throws NotFoundException {
        return ResponseEntity.ok(resourceAssembler.assemblePaymentsResource(paymentService.getPaymentsForOrderId(orderId)));
    }
}
