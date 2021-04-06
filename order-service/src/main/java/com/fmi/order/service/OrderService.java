package com.fmi.order.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.dao.entity.*;
import com.fmi.dao.repository.OrderProductRepository;
import com.fmi.dao.repository.OrderRepository;
import com.fmi.dao.repository.PaymentRepository;
import com.fmi.dao.repository.ProductRepository;
import com.fmi.order.mapper.OrderMapper;
import com.fmi.order.model.Order;
import com.fmi.order.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final OrderProductRepository orderProductRepository;

    private final PaymentRepository paymentRepository;

    private final OrderMapper orderMapper;

    public Order getById(Long id) throws NotFoundException {
        final Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (!optionalOrder.isPresent()) {
            throw new NotFoundException("Not found");
        }

        return orderMapper.mapFromEntity(optionalOrder.get());
    }

    public Page<Order> getOrdersByUserId(Long userId, Pageable pageable) throws NotFoundException {
        if (!orderRepository.findById(userId).isPresent()) {
            throw new NotFoundException("User doesn't exist");
        }

        return orderRepository.findAllByUserId(userId, pageable).map(orderMapper::mapFromEntity);
    }

    public void saveOrder(Order order, Payment payment, Map<Long, Integer> products) throws NotFoundException {
        final OrderEntity orderEntity = orderMapper.mapToEntity(order);
        orderRepository.save(orderEntity);

        for (Long productId : products.keySet()) {
            final Optional<ProductEntity> optionalProductEntity = productRepository.findById(productId);
            if (optionalProductEntity.isPresent()) {
                final OrderProductEntity orderProductEntity = new OrderProductEntity();
                final ProductEntity productEntity = optionalProductEntity.get();

                orderProductEntity.setOrder(orderEntity);
                orderProductEntity.setProduct(productEntity);
                orderProductEntity.setQuantity(products.get(productId));
                orderProductEntity.setOrderProductId(new OrderProductId(orderEntity.getId(), productEntity.getId()));

                orderProductRepository.save(orderProductEntity);
            } else {
                throw new NotFoundException("Product does with id: " + productId + " does not exist!");
            }
        }
        final PaymentEntity paymentEntity = orderMapper.mapToEntity(payment);
        paymentEntity.setOrder(orderEntity);
        paymentRepository.save(paymentEntity);
    }
}
