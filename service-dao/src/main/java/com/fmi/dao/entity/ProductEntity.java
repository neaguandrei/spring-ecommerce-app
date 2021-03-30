package com.fmi.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;

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

    public enum ProductCategory {
        MONITORS("Monitors"),
        HARDWARE("Hardware"),
        PERIPHERALS("Peripherals"),
        NONE("None");

        private String value;

        ProductCategory(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static ProductCategory fromValue(String value) {
            return Arrays.stream(values())
                    .filter(productCategory -> productCategory.getValue().equals(value))
                    .findFirst()
                    .orElse(null);
        }
    }
}
