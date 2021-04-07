package com.fmi.order.model;

import com.fmi.api.payment.CurrencyDto;
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

}
