package com.fmi.catalog.mapper;

import com.fmi.api.catalog.OrderDto;
import com.fmi.api.catalog.resource.request.CreateOrderRequestResource;
import com.fmi.api.payment.enums.CurrencyDto;
import com.fmi.api.payment.PaymentDto;
import com.fmi.catalog.dao.entity.OrderEntity;
import com.fmi.catalog.dao.entity.OrderProductEntity;
import com.fmi.catalog.dao.entity.OrderProductId;
import com.fmi.catalog.model.Order;
import com.fmi.catalog.model.Payment;
import com.fmi.catalog.model.enums.PaymentMethod;
import com.fmi.catalog.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class OrderMapper {

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
        payment.setCurrency(CurrencyDto.valueOf(paymentDto.getCurrency()));

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
//
//    public PaymentEntity mapToEntity(Payment payment) {
//        return modelMapper.map(payment, PaymentEntity.class);
//    }

}
