package com.fmi.catalog.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {

    private Long id;

    private String name;

    private String description;

    private Long quantity;

    private Long price;

    private String category;

    private String productLine;

    private String imagePath;

    private String imageFileName;
}
