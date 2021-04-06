package com.fmi.order.service.gateway;

import com.fmi.api.payment.PaymentDto;
import com.fmi.common.exception.BadRequestException;
import com.fmi.order.mapper.OrderMapper;
import com.fmi.order.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class PaymentGatewayService {

    private final OrderMapper orderMapper;

    private final RestTemplate restTemplate;

    @Value("${payment.api.endpoint.baseURL}")
    private String paymentUrl;

    @Retryable(
            value = {ResourceAccessException.class},
            maxAttemptsExpression = "5",
            backoff = @Backoff(delayExpression = "1000", maxDelayExpression = "1001")
    )
    public Payment executePayment(PaymentDto paymentDto) {
        final RequestEntity<PaymentDto> requestEntity = RequestEntity.post(paymentUrl).body(paymentDto);
        restTemplate.exchange(requestEntity, Object.class);
        return orderMapper.mapFromDto(paymentDto);
    }

    @Recover
    public Payment executePayment(Exception ex) throws BadRequestException {
        throw new BadRequestException(ex.getLocalizedMessage());
    }
}
