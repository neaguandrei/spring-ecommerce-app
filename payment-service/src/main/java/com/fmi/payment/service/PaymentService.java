package com.fmi.payment.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.payment.dao.entity.PaymentEntity;
import com.fmi.payment.dao.repository.PaymentRepository;
import com.fmi.payment.model.Order;
import com.fmi.payment.model.Payment;
import com.fmi.payment.mapper.PaymentMapper;
import com.fmi.payment.service.gateway.OrderGatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final OrderGatewayService orderGatewayService;

    private final PaymentMapper paymentMapper;

    public Payment getById(Long id) throws NotFoundException {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent()) {
            throw new NotFoundException("Payment not found!");
        }

        return paymentMapper.mapFromEntity(optionalPayment.get());
    }

    public List<Payment> getPaymentsByUserId(Long userId) throws NotFoundException {
        final List<Order> orders = orderGatewayService.getOrdersByUserId(userId);
        if (Objects.isNull(orders) || orders.isEmpty()) {
            throw new NotFoundException("No payments realised by this user.");
        }

        final List<Payment> payments = new ArrayList<>();
        paymentRepository
                .findByOrderIdIn(orders.stream().map(Order::getId).collect(Collectors.toList()))
                .forEach(payment -> payments.add(paymentMapper.mapFromEntity(payment)));

        return payments;
    }

}
