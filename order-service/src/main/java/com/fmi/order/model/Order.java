package com.fmi.order.model;

import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class Order {

    private Long id;

    private String comment;

    private Status status;

    private List<Long> productIds;

    private Long userId;

    private Long paymentId;

    private Date created;
}