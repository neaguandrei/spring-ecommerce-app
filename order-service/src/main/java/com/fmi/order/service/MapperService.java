package com.fmi.order.service;

import com.fmi.dao.entity.OrderEntity;
import com.fmi.dao.entity.OrderProductEntity;
import com.fmi.dao.entity.OrderProductId;
import com.fmi.dao.entity.PaymentEntity;
import com.fmi.order.dto.CreateOrderRequestResource;
import com.fmi.order.dto.OrderDto;
import com.fmi.order.dto.PaymentDto;
import com.fmi.order.model.Order;
import com.fmi.order.model.Payment;
import com.fmi.order.model.PaymentMethod;
import com.fmi.order.model.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    public Order mapFromEntity(OrderEntity orderEntity) {
        modelMapper.typeMap(OrderEntity.class, Order.class).addMappings(mapping -> mapping.skip(Order::setProductIds));
        final Order order = modelMapper.map(orderEntity, Order.class);
        order.setProductIds(orderEntity.getProducts().stream().map(OrderProductEntity::getOrderProductId).map(OrderProductId::getProductId).collect(Collectors.toList()));

        return order;
    }

    public Order mapFromDto(CreateOrderRequestResource resource) {
        final Order order = new Order();
        order.setComment(resource.getComment());
        order.setStatus(Status.valueOf(resource.getStatus()));
        order.setUserId(resource.getUserId());

        return order;
    }

    public Payment mapFromDto(PaymentDto paymentDto) {
        final Payment payment = new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentDto.getPaymentMethod()));

        return payment;
    }

    public OrderDto mapToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public Page<OrderDto> mapToDto(Page<Order> order) {
        return order.map(this::mapToDto);
    }

    public OrderEntity mapToEntity(Order orderDto) {
        return modelMapper.map(orderDto, OrderEntity.class);
    }

    public PaymentEntity mapToEntity(Payment payment) {
        return modelMapper.map(payment, PaymentEntity.class);
    }

}
