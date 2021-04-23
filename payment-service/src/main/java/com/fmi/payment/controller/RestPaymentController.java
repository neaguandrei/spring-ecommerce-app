package com.fmi.payment.controller;


import com.fmi.api.payment.PaymentDto;
import com.fmi.api.payment.PaymentCreationResponseResource;
import com.fmi.api.payment.PaymentResponseResource;
import com.fmi.payment.mapper.PaymentMapper;
import com.fmi.payment.service.gateway.PaymentProcessingGatewayService;
import com.fmi.payment.service.PaymentService;
import com.fmi.payment.assembler.ResourceAssembler;
import com.fmi.common.exception.NotFoundException;
import com.fmi.security.annotation.PreAuthorizeAny;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class RestPaymentController {

    private final PaymentService paymentService;

    private final PaymentProcessingGatewayService paymentProcessingGatewayService;

    private final PaymentMapper paymentMapper;

    private final ResourceAssembler resourceAssembler;

    @PostMapping
    public ResponseEntity<PaymentCreationResponseResource> processPayment(@RequestBody @Valid PaymentDto payment) {
        final Long paymentId = paymentProcessingGatewayService.sendToProcessing(paymentMapper.mapFromDto(payment));
        if (Objects.isNull(paymentId)) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(new PaymentCreationResponseResource(paymentId));
    }

    @PreAuthorizeAny
    @GetMapping(value = "/{payment_id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable(value = "payment_id") Long paymentId) throws NotFoundException {
        return ResponseEntity.ok(paymentMapper.mapToDto(paymentService.getById(paymentId)));
    }

    @PreAuthorize("#userId == authentication.principal.id and hasAnyAuthority('ADMIN','USER')")
    @GetMapping(value = "/users/{user_id}", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PaymentResponseResource>> getPaymentsForUserId(@PathVariable(value = "user_id") Long userId) throws NotFoundException {
        final List<PaymentResponseResource> responseResources = paymentService.getPaymentsByUserId(userId).stream()
                .map(paymentMapper::mapToResource)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resourceAssembler.assemblePaymentsResource(responseResources));
    }
}
