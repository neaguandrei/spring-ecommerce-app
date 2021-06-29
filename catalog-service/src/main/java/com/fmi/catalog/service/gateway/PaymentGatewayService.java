package com.fmi.catalog.service.gateway;

import com.fmi.api.payment.PaymentCreationResponseResource;
import com.fmi.catalog.model.Payment;
import com.fmi.common.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PaymentGatewayService {

    private final RestTemplate restTemplate;

    @Value("${payment.api.endpoint.baseURL}")
    private String paymentUrl;

    @Retryable(
            value = {ResourceAccessException.class},
            maxAttemptsExpression = "5",
            backoff = @Backoff(delayExpression = "1000", maxDelayExpression = "1001")
    )
    public Long executePayment(Payment paymentDto) throws BadRequestException {
        final RequestEntity<Payment> requestEntity = RequestEntity.post(paymentUrl).body(paymentDto);
        final ResponseEntity<PaymentCreationResponseResource> responseResource = restTemplate.exchange(requestEntity, PaymentCreationResponseResource.class);
        if (responseResource.getBody() == null || responseResource.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
            throw new BadRequestException("The payment couldn't be processed.");
        }

        return Optional.ofNullable(responseResource.getBody()).map(PaymentCreationResponseResource::getId)
                .orElseThrow(() -> new BadRequestException("The payment couldn't be processed."));
    }

    @Recover
    public Payment executePayment(Exception ex) throws BadRequestException {
        throw new BadRequestException(ex.getLocalizedMessage());
    }
}
