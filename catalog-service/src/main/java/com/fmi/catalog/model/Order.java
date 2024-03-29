package com.fmi.catalog.model;

import com.fmi.catalog.model.enums.Status;
import lombok.*;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private Long id;

    private String comment;

    private Status status;

    private List<Long> productIds;

    private Long userId;

    private Long paymentId;

    private Date created;
}