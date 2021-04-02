package com.fmi.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Getter
@Setter
@Builder
@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductCategory category;

    @Column(name = "product_line", nullable = false)
    private String productLine;

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

    public enum ProductCategory {
        MONITORS,
        HARDWARE,
        PERIPHERALS,
        NONE;
    }
}
