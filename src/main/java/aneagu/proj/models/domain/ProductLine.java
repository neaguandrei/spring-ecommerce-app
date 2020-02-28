package aneagu.proj.models.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "productLines")
public class ProductLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;

    @Column(nullable = false)
    private String textDescription;

    @Lob
    private Blob image;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "productLine",
            orphanRemoval = true
    )
    private Set<Product> product;
}
