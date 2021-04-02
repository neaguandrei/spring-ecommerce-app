package com.fmi.order.dto;

import com.fmi.common.validation.OneOf;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Data
public class PaymentDto {

    @NotNull(message = "Amount can't be empty.")
    @Size(min = 1, max = 10000)
    private BigDecimal amount;

    @NotNull(message = "Payment method can't be empty.")
    @OneOf(enumClass = PaymentMethod.class, message = "Method isn't of correct type.")
    private String paymentMethod;

    public enum PaymentMethod {
        PAYPAL,
        MASTERCARD,
        VISA,
        CASH;
    }
}
