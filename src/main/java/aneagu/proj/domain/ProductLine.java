package aneagu.proj.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;

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
    private String productLine;

    @Column(nullable = false)
    private String textDescription;

    @Column(nullable = false)
    @Lob
    private Blob image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
