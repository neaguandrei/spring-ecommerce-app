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

    public OrderDto getByInternalId(String id) throws NotFoundException {
        Optional<OrderEntity> optionalOrder = orderRepository.findByInternalId(id);
        if (!optionalOrder.isPresent()) {
            throw new NotFoundException("Not found");
        }

        return mapperService.convertOrderToOrderDto(optionalOrder.get());
    }


    public Page<OrderDto> getOrdersByUserInternalId(String userId, Pageable pageable) throws NotFoundException {
        if (!orderRepository.findByInternalId(userId).isPresent()) {
            throw new NotFoundException("Customer doesn't exist");
        }

        return orderRepository.findAllByUserId(userId, pageable).map(mapperService::convertOrderToOrderDto);
    }
}
