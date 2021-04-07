package com.fmi.payment.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private Long id;

    private String comment;

    private Long userId;
}
