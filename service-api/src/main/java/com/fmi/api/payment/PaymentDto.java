package com.fmi.api.payment;

import com.fmi.common.validation.OneOf;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class PaymentDto {

    @NotNull(message = "Amount can't be empty.")
    @Size(min = 1, max = 1000)
    private BigDecimal amount;

    @NotNull(message = "Currency can't be empty.")
    @OneOf(enumClass = CurrencyDto.class, message = "Currency isn't of correct type.")
    private String currency;

    @NotNull(message = "Payment method can't be empty.")
    @OneOf(enumClass = PaymentMethodDto.class, message = "Method isn't of correct type.")
    private String paymentMethod;

    private Long orderId;
}
