package aneagu.proj.models.dto;

import aneagu.proj.models.enums.ProductCategory;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Relation(collectionRelation = "products")
public class ProductDto extends RepresentationModel<ProductDto> {

    private Long id;

    @NotNull(message = "Name can't be empty.")
    private String name;

    @NotNull(message = "Description can't be empty.")
    private String description;

    @NotNull(message = "Quantity can't be empty.")
    @Size(max = 30)
    private Long quantityInStock;

    @NotNull(message = "Buy price can't be empty.")
    @Size(min = 1)
    private Long buyPrice;

    private ProductCategory category;

    private ProductLineDto productLine;
}
