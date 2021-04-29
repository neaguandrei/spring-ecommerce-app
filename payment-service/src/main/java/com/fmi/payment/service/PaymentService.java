package com.fmi.payment.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.payment.dao.entity.PaymentEntity;
import com.fmi.payment.dao.repository.PaymentRepository;
import com.fmi.payment.model.Payment;
import com.fmi.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    public Payment getById(Long id) throws NotFoundException {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent()) {
            throw new NotFoundException("Payment not found!");
        }

        return paymentMapper.mapFromEntity(optionalPayment.get());
    }

    public List<Payment> getPaymentsByUserId(Long userId) throws NotFoundException {
        final List<Payment> payments = new ArrayList<>();
        Iterable<PaymentEntity> paymentEntities = Optional.ofNullable(paymentRepository.findAllByUserId(userId))
                .orElseThrow(() -> new NotFoundException("No payments for userId:" + userId));
        paymentEntities.forEach(paymentEntity -> payments.add(paymentMapper.mapFromEntity(paymentEntity)));

        return payments;
    }

}
