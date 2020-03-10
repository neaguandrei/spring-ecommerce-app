package aneagu.proj.models.dto;

import aneagu.proj.models.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class)
public class ProductDto {

    private Long id;

    @NotNull(message = "Name can't be empty.")
    private String name;

    @NotNull(message = "Description can't be empty.")
    private String description;

    @NotNull(message = "Quantity can't be empty.")
    private Long quantityInStock;

    @NotNull(message = "Buy price can't be empty.")
    private Long buyPrice;

    private ProductCategory category;

    private Set<ProductLineDto> products;

    private Set<OrderDto> orders;
}
