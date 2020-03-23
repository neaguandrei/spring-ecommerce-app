package aneagu.proj.service;

import aneagu.proj.models.dto.OrderDto;
import aneagu.proj.models.exception.NotFoundException;

import java.util.List;

public interface OrderService {

    OrderDto get(Long id) throws NotFoundException;

    List<OrderDto> getOrdersByUserId(Long userId) throws NotFoundException;
}
