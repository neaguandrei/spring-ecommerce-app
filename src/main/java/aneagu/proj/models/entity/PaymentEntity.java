package aneagu.proj.models.entity;

import aneagu.proj.models.enums.PaymentMethod;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor()
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "payments")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @Column(insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    private LocalDateTime created;

    @Column(insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    private LocalDateTime lastUpdated;

    @Version
    @Column
    @Setter(AccessLevel.NONE)
    private Integer version;

    public PaymentEntity(Long id, BigDecimal amount, PaymentMethod paymentMethod, OrderEntity order) {
        this.id = id;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.order = order;
    }
}
