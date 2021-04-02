package com.fmi.payment.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.common.validation.OneOf;
import com.fmi.dao.entity.PaymentEntity;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Arrays;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Relation(collectionRelation = "payments")
public class PaymentDto extends RepresentationModel<PaymentDto> {

    private Long id;

    @NotNull(message = "Amount can't be empty.")
    @Size(min = 1, max = 1000)
    private BigDecimal amount;

    @NotNull(message = "Payment method can't be empty.")
    @OneOf(enumClass = PaymentMethod.class, message = "Method isn't of correct type.")
    private String paymentMethod;

    private Long orderId;

    public enum PaymentMethod {
        PAYPAL,
        MASTERCARD,
        VISA,
        CASH;
    }
}
