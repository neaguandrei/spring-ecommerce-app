package com.fmi.cart.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cartKey;

    private String email;

    @Builder.Default
    private Map<Long, Integer> products = new HashMap<>();

    private LocalDateTime created;

    private LocalDateTime lastUpdated;

    private LocalDateTime deleted;

}
