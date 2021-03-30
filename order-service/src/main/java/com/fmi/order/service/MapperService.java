package com.fmi.order.service;

import com.fmi.dao.entity.OrderEntity;
import com.fmi.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    public OrderEntity convertOrderDtoToOrder(OrderDto orderDto) {
        return modelMapper.map(orderDto, OrderEntity.class);
    }

    public OrderDto convertOrderToOrderDto(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDto.class);
    }
}
