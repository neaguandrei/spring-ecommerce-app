package com.fmi.payment.model;

import lombok.Data;

@Data
public class Order {

    private Long id;

    private String comment;

    private Long userId;
}
