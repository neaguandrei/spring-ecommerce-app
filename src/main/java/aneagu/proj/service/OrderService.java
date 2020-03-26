package aneagu.proj.service;

import aneagu.proj.models.dto.OrderDto;
import aneagu.proj.models.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    OrderDto get(Long id) throws NotFoundException;

    Page<OrderDto> getOrdersByUserId(Long userId, Pageable pageable) throws NotFoundException;
}
