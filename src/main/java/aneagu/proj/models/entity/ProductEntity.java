package aneagu.proj.models.entity;

import aneagu.proj.models.enums.ProductCategory;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@Getter
@Setter
@Builder
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long quantityInStock;

    @Column(nullable = false)
    private Long buyPrice;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductCategory category;

    @Column(nullable = false)
    private String productLine;

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

    public ProductEntity(Long id, String name, String description, Long quantityInStock, Long buyPrice, ProductCategory category, String productLine) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.buyPrice = buyPrice;
        this.category = category;
        this.productLine = productLine;
    }
}
