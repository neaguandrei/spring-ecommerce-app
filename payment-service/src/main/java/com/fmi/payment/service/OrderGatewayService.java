package com.fmi.payment.service;

import com.fmi.payment.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrderGatewayService {

    private final RestTemplate restTemplate;

    @Value("${order.api.endpoint.baseURL}")
    private String orderUrl;

    public OrderDto getOrderById(String orderId) {
        try {
            final ResponseEntity<OrderDto> responseEntity = restTemplate.getForEntity(orderUrl + "/{orderId}", OrderDto.class, orderId);
            return responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}
