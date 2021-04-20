package com.fmi.payment.mapper;

import com.fmi.api.catalog.OrderDto;
import com.fmi.api.payment.PaymentDto;
import com.fmi.api.payment.PaymentCreationResponseResource;
import com.fmi.api.payment.PaymentResponseResource;
import com.fmi.payment.dao.entity.PaymentEntity;
import com.fmi.payment.model.Order;
import com.fmi.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final ModelMapper modelMapper;

    public PaymentResponseResource mapToResource(Payment payment) {
        return modelMapper.map(payment, PaymentResponseResource.class);
    }

    public PaymentCreationResponseResource mapToCreateResponseResource(Payment payment) {
        return new PaymentCreationResponseResource(payment.getId());
    }

    public Payment mapFromDto(PaymentDto paymentEntity) {
        return modelMapper.map(paymentEntity, Payment.class);
    }

    public PaymentEntity mapToEntity(Payment payment) {
        return modelMapper.map(payment, PaymentEntity.class);
    }

    public Payment mapFromEntity(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, Payment.class);
    }

    public Order mapFromDto(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public List<Order> mapFromPageOrderDto(Page<OrderDto> orderDtoPage) {
        return orderDtoPage.map(this::mapFromDto).stream().collect(Collectors.toList());
    }

    public PaymentDto mapToDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }
}
