package com.fmi.payment.model;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    private Long id;

    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    private String description;

    private Long orderId;

    private Long userId;
}
