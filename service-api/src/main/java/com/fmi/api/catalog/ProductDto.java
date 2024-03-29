package com.fmi.api.catalog;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.common.validation.OneOf;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotNull(message = "Name can't be empty.")
    private String name;

    @NotNull(message = "Description can't be empty.")
    private String description;

    @NotNull(message = "Quantity can't be empty.")
    @Size(max = 1500)
    private Long quantity;

    @NotNull(message = "Buy price can't be empty.")
    @Size(min = 1)
    private Long price;

    @NotNull(message = "Product category can't be empty.")
    @OneOf(enumClass = ProductCategoryDto.class, message = "Category isn't of correct type.")
    private String category;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productLine;

    private String imagePath;

    private String imageFileName;
}
