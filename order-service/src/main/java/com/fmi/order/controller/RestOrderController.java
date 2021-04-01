package com.fmi.order.controller;


import com.fmi.common.exception.NotFoundException;
import com.fmi.order.dto.OrderDto;
import com.fmi.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class RestOrderController {

    private final OrderService orderService;

    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) throws NotFoundException {
        return ResponseEntity.ok(orderService.getById(orderId));
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<Page<OrderDto>> getOrdersForUserId(@PathVariable Long userId,
                                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                                             @RequestParam(name = "size", defaultValue = "10") int size,
                                                             @RequestParam(name = "sort", defaultValue = "DESC") String sort,
                                                             @RequestParam(name = "sortedParam", defaultValue = "date") String sortedParam) throws NotFoundException {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId, PageRequest.of(page, size, Sort.Direction.valueOf(sort), sortedParam)));
    }
}
