package com.fmi.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

@AllArgsConstructor()
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "PAYMENT")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(updatable = false, nullable = false)
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;

    @Column(name = "last_updated", nullable = false)
    @Setter(AccessLevel.NONE)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdated;


    @Version
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private Integer version;

    public enum PaymentMethod {
        PAYPAL("PayPal"),
        MASTER_CARD("MasterCard"),
        VISA_CARD("VisaCard"),
        CASH("Cash");

        private String value;

        PaymentMethod(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public PaymentMethod fromValue(String value) {
            return Arrays.stream(PaymentMethod.values())
                    .filter(method -> method.getValue().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Value doesn't exist!"));
        }
    }
}
