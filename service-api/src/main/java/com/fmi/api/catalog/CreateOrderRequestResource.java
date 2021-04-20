package com.fmi.api.catalog;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fmi.api.payment.PaymentDto;
import com.fmi.common.validation.OneOf;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestResource {

    @Size(max = 450)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comment;

    @NotNull
    @OneOf(enumClass = StatusDto.class, message = "Incorrect status type.")
    private String status;

    @NotNull
    private Map<Long, Integer> products;

    @NotNull
    @Valid
    private PaymentDto payment;

}
