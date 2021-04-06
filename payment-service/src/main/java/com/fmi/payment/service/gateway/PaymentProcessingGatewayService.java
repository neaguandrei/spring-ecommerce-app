package com.fmi.payment.service.gateway;

import com.fmi.payment.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentProcessingGatewayService {

    public boolean sendToProcessing(Payment object) {
        return Objects.nonNull(object);
    }
}
