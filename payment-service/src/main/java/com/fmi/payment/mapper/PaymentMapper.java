package com.fmi.payment.mapper;

import com.fmi.api.order.OrderDto;
import com.fmi.api.payment.PaymentDto;
import com.fmi.api.payment.PaymentResponseResource;
import com.fmi.dao.entity.PaymentEntity;
import com.fmi.payment.model.Order;
import com.fmi.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentMapper {

    private final ModelMapper modelMapper;

    public PaymentResponseResource mapToResource(Payment payment) {
        return modelMapper.map(payment, PaymentResponseResource.class);
    }

    public Payment mapOrderFromDto(PaymentDto paymentEntity) {
        return modelMapper.map(paymentEntity, Payment.class);
    }

    public PaymentEntity mapToEntity(Payment payment) {
        return modelMapper.map(payment, PaymentEntity.class);
    }

    public Payment mapFromEntity(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, Payment.class);
    }

    public Order mapOrderFromDto(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public PaymentDto mapToDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }
}
