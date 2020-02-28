package aneagu.proj.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    PAYPAL("PayPal"),
    MASTER_CARD("MasterCard"),
    VISA_CARD("VisaCard"),
    CASH("Cash");

    private String value;

    public PaymentMethod fromValue(String value) {
        return Arrays.stream(PaymentMethod.values())
                .filter(paymentMethod -> paymentMethod.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Value doesn't exist!"));
    }
}
