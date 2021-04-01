package com.fmi.payment.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.dao.entity.PaymentEntity;
import com.fmi.dao.repository.PaymentRepository;
import com.fmi.payment.dto.PaymentDto;
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

    private final MapperService mapperService;

    public void save(PaymentDto object) {
        paymentRepository.save(mapperService.convertPaymentDtoToPayment(object));
    }

    public PaymentDto getById(Long id) throws NotFoundException {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent()) {
            throw new NotFoundException("Payment not found!");
        }

        return mapperService.convertPaymentToPaymentDto(optionalPayment.get());
    }

    public List<PaymentDto> getPaymentsForOrderId(String orderId) throws NotFoundException {
        if (Objects.isNull(orderGatewayService.getOrderById(orderId))) {
            throw new NotFoundException("Order doesn't exist.");
        }

        List<PaymentDto> paymentDtos = new ArrayList<>();
        paymentRepository.findAllByOrderId(orderId).forEach(payment -> paymentDtos.add(mapperService.convertPaymentToPaymentDto(payment)));

        return paymentDtos;
    }

}
