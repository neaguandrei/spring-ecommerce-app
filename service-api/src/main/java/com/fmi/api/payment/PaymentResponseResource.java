package com.fmi.api.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;


@EqualsAndHashCode(callSuper = true)
@Data
@Relation(collectionRelation = "payments")
public class PaymentResponseResource extends RepresentationModel<PaymentResponseResource> {

    private Long id;

    private BigDecimal amount;

    private String paymentMethod;

    private Long orderId;

}
