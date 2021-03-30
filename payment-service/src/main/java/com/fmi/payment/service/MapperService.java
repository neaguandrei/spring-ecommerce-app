package com.fmi.payment.service;

import com.fmi.dao.entity.PaymentEntity;
import com.fmi.payment.dto.PaymentDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    public PaymentEntity convertPaymentDtoToPayment(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, PaymentEntity.class);
    }

    public PaymentDto convertPaymentToPaymentDto(PaymentEntity paymentEntity) {
        return modelMapper.map(paymentEntity, PaymentDto.class);
    }
}
