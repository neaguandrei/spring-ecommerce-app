package com.fmi.order.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.dao.entity.OrderEntity;
import com.fmi.dao.repository.OrderRepository;
import com.fmi.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final MapperService mapperService;

    public OrderDto getById(Long id) throws NotFoundException {
        Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new NotFoundException("Not found");
        }

        return mapperService.convertOrderToOrderDto(optionalOrder.get());
    }


    public Page<OrderDto> getOrdersByUserId(Long userId, Pageable pageable) throws NotFoundException {
        if (!orderRepository.findById(userId).isPresent()) {
            throw new NotFoundException("User doesn't exist");
        }

        return orderRepository.findAllByUserId(userId, pageable).map(mapperService::convertOrderToOrderDto);
    }
}
