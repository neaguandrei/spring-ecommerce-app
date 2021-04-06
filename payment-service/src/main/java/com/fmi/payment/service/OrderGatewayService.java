package com.fmi.payment.service;

import com.fmi.api.order.OrderDto;
import com.fmi.common.rest.RestResponsePage;
import com.fmi.payment.mapper.PaymentMapper;
import com.fmi.payment.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderGatewayService {

    private final PaymentMapper paymentMapper;

    private final RestTemplate restTemplate;

    @Value("${order.api.endpoint.baseURL}")
    private String orderUrl;

    public List<Order> getOrdersByUserId(Long userId) {
        ParameterizedTypeReference<RestResponsePage<OrderDto>> responseType = new ParameterizedTypeReference<RestResponsePage<OrderDto>>() { };
        ResponseEntity<RestResponsePage<OrderDto>> responseEntity = restTemplate.exchange(orderUrl + "/users/" + userId, HttpMethod.GET, null, responseType);

        return Optional.ofNullable(responseEntity.getBody()).map(paymentMapper::mapFromPageOrderDto).orElse(null);
    }
}
