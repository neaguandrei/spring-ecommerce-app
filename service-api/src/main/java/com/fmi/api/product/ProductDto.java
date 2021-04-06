package com.fmi.api.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.common.validation.OneOf;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    @NotNull(message = "Name can't be empty.")
    private String name;

    @NotNull(message = "Description can't be empty.")
    private String description;

    @NotNull(message = "Quantity can't be empty.")
    @Size(max = 30)
    private Long quantity;

    @NotNull(message = "Buy price can't be empty.")
    @Size(min = 1)
    private Long price;

    @NotNull(message = "Product category can't be empty.")
    @OneOf(enumClass = ProductCategoryDto.class, message = "Category isn't of correct type.")
    private String category;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String productLine;

}
