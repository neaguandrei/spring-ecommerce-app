package com.fmi.order.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.dao.entity.OrderEntity;
import com.fmi.dao.entity.OrderProductEntity;
import com.fmi.dao.entity.ProductEntity;
import com.fmi.dao.repository.OrderRepository;
import com.fmi.dao.repository.ProductRepository;
import com.fmi.order.model.Order;
import com.fmi.order.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final MapperService mapperService;

    public Order getById(Long id) throws NotFoundException {
        final Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new NotFoundException("Not found");
        }

        return mapperService.mapFromEntity(optionalOrder.get());
    }

    public Page<Order> getOrdersByUserId(Long userId, Pageable pageable) throws NotFoundException {
        if (!orderRepository.findById(userId).isPresent()) {
            throw new NotFoundException("User doesn't exist");
        }

        return orderRepository.findAllByUserId(userId, pageable).map(mapperService::mapFromEntity);
    }

    public void saveOrder(Order order, Payment payment, Map<Long, Integer> products) throws NotFoundException {
        final OrderEntity orderEntity = mapperService.mapToEntity(order);
        final List<OrderProductEntity> orderProducts = new ArrayList<>();
        for (Long productId : products.keySet()) {
            final Optional<ProductEntity> productEntity = productRepository.findById(productId);
            if (productEntity.isPresent()) {
                final OrderProductEntity orderProductEntity = new OrderProductEntity();
                orderProductEntity.setQuantity(products.get(productId));
                orderProducts.add(orderProductEntity);
            } else {
                throw new NotFoundException("Product does with id: " + productId + " does not exist!");
            }
        }
        orderEntity.setPayment(mapperService.mapToEntity(payment));
        orderEntity.setProducts(orderProducts);
        orderRepository.save(orderEntity);
    }

}
