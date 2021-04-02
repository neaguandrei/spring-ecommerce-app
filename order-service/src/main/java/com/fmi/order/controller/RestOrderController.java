package com.fmi.order.controller;


import com.fmi.common.exception.NotFoundException;
import com.fmi.order.dto.CreateOrderRequestResource;
import com.fmi.order.dto.OrderDto;
import com.fmi.order.model.Order;
import com.fmi.order.model.Payment;
import com.fmi.order.service.MapperService;
import com.fmi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class RestOrderController {

    private final OrderService orderService;

    private final MapperService mapperService;

    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) throws NotFoundException {
        return ResponseEntity.ok(mapperService.mapToDto(orderService.getById(orderId)));
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<Page<OrderDto>> getOrdersForUserId(@PathVariable Long userId,
                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                                             @RequestParam(name = "sort", defaultValue = "DESC") String sort) throws NotFoundException {
        return ResponseEntity.ok(mapperService.mapToDto(orderService.getOrdersByUserId(userId, PageRequest.of(page, size, Sort.Direction.valueOf(sort), "created"))));
    }

    @PostMapping
    public ResponseEntity<Object> saveOrder(@RequestBody @NotNull CreateOrderRequestResource request) throws NotFoundException {
        final Order order = mapperService.mapFromDto(request);
        final Payment payment = mapperService.mapFromDto(request.getPayment());
        orderService.saveOrder(order, payment, request.getProducts());

        return ResponseEntity.noContent().build();
    }
}
