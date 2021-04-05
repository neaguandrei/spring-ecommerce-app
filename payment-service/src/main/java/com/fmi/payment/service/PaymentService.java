package com.fmi.payment.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.dao.entity.PaymentEntity;
import com.fmi.dao.repository.PaymentRepository;
import com.fmi.payment.model.Payment;
import com.fmi.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderGatewayService orderGatewayService;

    private final PaymentMapper paymentMapper;

    public void save(Payment object) {
        paymentRepository.save(paymentMapper.mapToEntity(object));
    }

    public Payment getById(Long id) throws NotFoundException {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent()) {
            throw new NotFoundException("Payment not found!");
        }

        return paymentMapper.mapFromEntity(optionalPayment.get());
    }

    public List<Payment> getPaymentsByUserId(Long orderId) throws NotFoundException {
        if (Objects.isNull(orderGatewayService.getOrderById(orderId))) {
            throw new NotFoundException("Order doesn't exist.");
        }

        List<Payment> payments = new ArrayList<>();
        paymentRepository.findAllByOrderId(orderId).forEach(payment -> payments.add(paymentMapper.mapFromEntity(payment)));

        return payments;
    }

}
