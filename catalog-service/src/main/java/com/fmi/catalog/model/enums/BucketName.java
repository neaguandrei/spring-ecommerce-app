package com.fmi.catalog.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    PRODUCT_IMAGE("spring-ecommerce-fmi");

    private final String name;
}