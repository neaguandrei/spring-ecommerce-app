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
import java.util.Arrays;


@EqualsAndHashCode(callSuper = true)
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "internalId")
@Relation(collectionRelation = "payments")
public class PaymentDto extends RepresentationModel<PaymentDto> {

    @NotNull
    private String internalId;

    @NotNull(message = "Amount can't be empty.")
    @Size(min = 1, max = 1000)
    private Long amount;

    @NotNull(message = "Payment method can't be empty.")
    @OneOf(enumClass = PaymentMethod.class, message = "Method isn't of correct type.")
    private String paymentMethod;

    private Long userId;

    public enum PaymentMethod {
        PAYPAL("PayPal"),
        MASTER_CARD("MasterCard"),
        VISA_CARD("VisaCard"),
        CASH("Cash");

        private String value;

        PaymentMethod(String value) {
            this.value = value;
        }
    }
}
