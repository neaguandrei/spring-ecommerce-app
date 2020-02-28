package aneagu.proj.models.dto;

import aneagu.proj.models.domain.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class)
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private Long quantityInStock;

    private Long buyPrice;

    private ProductCategory category;

    private Set<ProductLineDto> products;

    private Set<OrderDetailsDto> orders;
}
