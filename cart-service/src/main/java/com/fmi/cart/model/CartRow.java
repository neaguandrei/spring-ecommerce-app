package com.fmi.cart.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartRow {

    private String cartKey;

    private Map<Long, Integer> products = new HashMap<>();

    private LocalDateTime deleted;

    private LocalDateTime lastUpdated;

    private LocalDateTime created;

}
