package com.fmi.api.payment;

import com.fmi.common.validation.OneOf;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    @NotNull(message = "Amount can't be empty.")
    @DecimalMax(value = "50000")
    @DecimalMin(value = "1")
    private BigDecimal amount;

    @NotNull(message = "Currency can't be empty.")
    @OneOf(enumClass = CurrencyDto.class, message = "Currency isn't of correct type.")
    private String currency;

    @NotNull(message = "Payment method can't be empty.")
    @OneOf(enumClass = PaymentMethodDto.class, message = "Method isn't of correct type.")
    private String paymentMethod;

    private Long userId;
}
