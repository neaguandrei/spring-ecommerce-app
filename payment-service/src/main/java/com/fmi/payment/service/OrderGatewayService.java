package com.fmi.payment.service;

import com.fmi.api.order.OrderDto;
import com.fmi.payment.mapper.PaymentMapper;
import com.fmi.payment.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderGatewayService {

    private final PaymentMapper paymentMapper;

    private final RestTemplate restTemplate;

    @Value("${order.api.endpoint.baseURL}")
    private String orderUrl;

    public Order getOrderById(Long orderId) {
        final ResponseEntity<OrderDto> responseEntity = restTemplate.getForEntity(orderUrl + "/{orderId}", OrderDto.class, orderId);
        return Optional.ofNullable(responseEntity.getBody()).map(paymentMapper::mapOrderFromDto)
                .orElse(null);
    }
}
