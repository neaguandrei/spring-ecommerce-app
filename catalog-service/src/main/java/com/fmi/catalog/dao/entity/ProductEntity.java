package com.fmi.catalog.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
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

    @Column(name = "image_path", nullable = false)
    private String imagePath;

    @Column(name = "image_file_name", nullable = false)
    private String imageFileName;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<OrderProductEntity> orders = new ArrayList<>();

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
        NONE
    }
}
