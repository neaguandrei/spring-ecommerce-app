package com.fmi.payment.service.gateway;

import com.fmi.payment.dao.entity.PaymentEntity;
import com.fmi.payment.dao.repository.PaymentRepository;
import com.fmi.payment.mapper.PaymentMapper;
import com.fmi.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProcessingGatewayService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    public Long sendToProcessing(Payment object) {
        final PaymentEntity paymentEntity = paymentMapper.mapToEntity(object);
        paymentRepository.save(paymentEntity);
        return paymentEntity.getId();
    }
}
