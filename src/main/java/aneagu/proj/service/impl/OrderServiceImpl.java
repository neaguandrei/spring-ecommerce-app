package aneagu.proj.service.impl;

//TODO: De pus Pageable pentru Order List, Payment List si Product List

import aneagu.proj.models.domain.Order;
import aneagu.proj.models.dto.OrderDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.OrderRepository;
import aneagu.proj.service.MapperService;
import aneagu.proj.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final MapperService mapperService;

    public OrderServiceImpl(OrderRepository orderRepository, MapperService mapperService) {
        this.orderRepository = orderRepository;
        this.mapperService = mapperService;
    }

    @Override
    public void save(OrderDto object) {
        orderRepository.save(mapperService.convertOrderDtoToOrder(object));
    }

    @Override
    public OrderDto get(Long id) throws NotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new NotFoundException("Not found");
        }

        return mapperService.convertOrderToOrderDto(optionalOrder.get());
    }

    @Override
    public List<OrderDto> getOrdersByUserId(Long userId) throws NotFoundException {
        if (!orderRepository.findById(userId).isPresent()) {
            throw new NotFoundException("Customer doesn't exist");
        }
        List<OrderDto> orderDtos = new ArrayList<>();
        orderRepository.findAllByCustomer_Id(userId)
                .forEach(order -> orderDtos.add(mapperService.convertOrderToOrderDto(order)));

        return orderDtos;
    }
}
