package com.fmi.payment.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest {

    private String description;

    private Integer total;

    private String currency;

    private String stripeEmail;

    private StripeToken stripeToken;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public enum StripeToken {
        VISA("tok_visa"),
        MASTERCARD("tok_mastercard");

        private String value;
    }
}
