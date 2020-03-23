package aneagu.proj.controller;

import aneagu.proj.models.dto.OrderDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(orderService.get(id));
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersForUserId(@PathVariable Long userId) throws NotFoundException {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }
}
