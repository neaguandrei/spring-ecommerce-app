package com.fmi.payment.model;

import lombok.*;
import java.math.BigDecimal;


@Data
public class Payment{

    private Long id;

    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    private Long orderId;
}
