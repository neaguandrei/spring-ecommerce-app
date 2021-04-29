package com.fmi.payment.service.gateway;

import com.fmi.common.exception.PaymentProcessingException;
import com.fmi.payment.dao.entity.PaymentEntity;
import com.fmi.payment.dao.repository.PaymentRepository;
import com.fmi.payment.mapper.PaymentMapper;
import com.fmi.payment.model.ChargeRequest;
import com.fmi.payment.model.Payment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StripeGatewayService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public Long process(Payment payment) throws PaymentProcessingException {
        final Charge chargeResult;
        try {
            chargeResult = charge(paymentMapper.mapToChargeRequest(payment));
        } catch (StripeException e) {
            throw new PaymentProcessingException(e.getMessage());
        }

        final PaymentEntity paymentEntity = paymentMapper.mapToEntity(payment);
        if (chargeResult.getStatus().equals("succeeded")) {
            paymentRepository.save(paymentEntity);
        }

        return paymentEntity.getId();
    }

    private Charge charge(ChargeRequest chargeRequest) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getTotal());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("source", chargeRequest.getStripeToken().getValue());
        chargeParams.put("receipt_email", chargeRequest.getStripeEmail());

        return Charge.create(chargeParams);
    }

}
