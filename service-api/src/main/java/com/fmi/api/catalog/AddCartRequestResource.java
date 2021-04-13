package com.fmi.api.catalog;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCartRequestResource {

    @NotNull
    private String cartKey;

    @NotNull
    private Long productId;
}
