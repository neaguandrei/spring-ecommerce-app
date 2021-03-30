package com.fmi.product.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fmi.common.validation.OneOf;
import com.fmi.dao.entity.ProductEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProductDto {

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

    @NotNull(message = "Product category can't be empty.")
    @OneOf(enumClass = ProductEntity.ProductCategory.class, message = "Category isn't of correct type.")
    private String category;

    private String productLine;
}
