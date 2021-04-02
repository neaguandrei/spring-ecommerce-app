package com.fmi.order.model;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class Payment {

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

}
