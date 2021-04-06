package com.fmi.order.model;

import com.fmi.api.payment.CurrencyDto;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class Payment {

    private BigDecimal amount;

    private CurrencyDto currency;

    private PaymentMethod paymentMethod;

}
