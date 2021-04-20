package com.fmi.catalog.controller;


import com.fmi.api.catalog.OrderDto;
import com.fmi.api.catalog.CreateOrderRequestResource;
import com.fmi.catalog.model.Payment;
import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.catalog.model.Order;
import com.fmi.catalog.mapper.OrderMapper;
import com.fmi.catalog.service.OrderService;
import com.fmi.catalog.service.gateway.PaymentGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class RestOrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @GetMapping(value = "/{order_id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("order_id") Long orderId) throws NotFoundException {
        return ResponseEntity.ok(orderMapper.mapToDto(orderService.getById(orderId)));
    }

    @GetMapping(value = "/users/{user_id}")
    public ResponseEntity<Page<OrderDto>> getOrdersForUserId(@PathVariable("user_id") Long userId,
                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                                             @RequestParam(name = "sort", defaultValue = "DESC") String sort) throws NotFoundException {
        return ResponseEntity.ok(orderMapper.mapToDto(orderService.getOrdersByUserId(userId, PageRequest.of(page, size, Sort.Direction.valueOf(sort), "created"))));
    }

    @PostMapping
    public ResponseEntity<Object> saveOrder(@Valid @RequestBody CreateOrderRequestResource request) throws NotFoundException, BadRequestException {
        final Order order = orderMapper.mapFromDto(request);
        orderService.saveOrder(order, orderMapper.mapFromDto(request.getPayment()), request.getProducts());
        return ResponseEntity.noContent().build();
    }
}
