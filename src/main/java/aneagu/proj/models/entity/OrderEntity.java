package aneagu.proj.models.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private PaymentEntity payment;

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
    private int version;

    public OrderEntity(Long id, String comment, UserEntity user, List<ProductEntity> products) {
        this.id = id;
        this.comment = comment;
        this.user = user;
        this.products = products;
    }
}
