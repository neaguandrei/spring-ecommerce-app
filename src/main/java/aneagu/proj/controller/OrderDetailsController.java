package aneagu.proj.controller;

import aneagu.proj.controller.utils.ControllerUtils;
import aneagu.proj.models.dto.*;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.OrderDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/order-details")
@RequiredArgsConstructor
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @GetMapping(value = "/{orderId}")
    public ResponseEntity<OrderDetailsWrapperDto> getOrderDetailsForOrderId(@PathVariable Long orderId) throws NotFoundException {
        return ResponseEntity.ok(orderDetailsService.get(orderId));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createOrderDetails(@RequestBody @Valid CreateOrderDetailsDto createOrderDetailsDto) throws NotFoundException {
        Long orderId = orderDetailsService.save(createOrderDetailsDto.getOrderDto(), createOrderDetailsDto.getProductDtosList(),
                createOrderDetailsDto.getOrderDetailSpecifics());
        return ResponseEntity
                .created(ControllerUtils.generateUri(orderId, "getOrderDetailsForOrderId",
                        OrderDetailsController.class).toUri())
                .build();
    }

}
