package com.fmi.dao.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Getter
@Setter
@Builder
@Entity
@Table(name = "ORDERS")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String comment;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    @OneToOne(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "order"
    )
    private PaymentEntity payment;

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
    private int version;

    public enum Status {
        APPROVED,
        CANCELLED,
        PENDING;
    }
}
