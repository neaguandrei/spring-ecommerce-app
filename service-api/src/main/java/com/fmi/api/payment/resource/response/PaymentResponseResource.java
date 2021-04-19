package com.fmi.api.payment.resource.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Relation(collectionRelation = "payments")
public class PaymentResponseResource extends RepresentationModel<PaymentResponseResource> {

    private Long id;

    private BigDecimal amount;

    private String currency;

    private String paymentMethod;

    private Long orderId;

}
