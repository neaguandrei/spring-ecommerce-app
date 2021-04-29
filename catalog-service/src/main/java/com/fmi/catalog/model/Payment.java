package com.fmi.catalog.model;

import com.fmi.api.payment.CurrencyDto;
import com.fmi.catalog.model.enums.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    private BigDecimal amount;

    private CurrencyDto currency;

    private PaymentMethod paymentMethod;

    private String description;

    private Long orderId;

    private Long userId;
}
