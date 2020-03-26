package aneagu.proj.service;

import aneagu.proj.models.domain.Order;
import aneagu.proj.models.dto.OrderDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.OrderRepository;
import aneagu.proj.service.MapperService;
import aneagu.proj.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final MapperService mapperService;

    @Override
    public OrderDto get(Long id) throws NotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new NotFoundException("Not found");
        }

        return mapperService.convertOrderToOrderDto(optionalOrder.get());
    }

    @Override
    public Page<OrderDto> getOrdersByUserId(Long userId, Pageable pageable) throws NotFoundException {
        if (!orderRepository.findById(userId).isPresent()) {
            throw new NotFoundException("Customer doesn't exist");
        }

        return orderRepository.findAllByCustomerId(userId, pageable)
                .map(mapperService::convertOrderToOrderDto);
    }
}
