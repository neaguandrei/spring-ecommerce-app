package com.fmi.catalog.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.api.catalog.ProductDto;
import com.fmi.api.catalog.ProductsResponseResource;
import com.fmi.catalog.dao.entity.ProductEntity;
import com.fmi.catalog.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final ModelMapper modelMapper;

    public Product mapFromDto(ProductDto product) {
        return modelMapper.map(product, Product.class);
    }

    public ProductDto mapToDto(Product product) {
        return modelMapper.map(product, ProductDto.class);
    }

    public ProductEntity mapToEntity(Product product) {
        return modelMapper.map(product, ProductEntity.class);
    }

    public Product mapFromEntity(ProductEntity productEntity) {
        return modelMapper.map(productEntity, Product.class);
    }

    public Page<ProductDto> mapToDto(Page<Product> products) {
        return products.map(this::mapToDto);
    }

    public ProductsResponseResource mapToResource(List<Product> products) {
        return new ProductsResponseResource(products.stream().map(this::mapToDto).collect(Collectors.toList()));
    }

    public ProductDto getDtoFromString(String product) throws JsonProcessingException {
        ProductDto productDto;
        ObjectMapper objectMapper = new ObjectMapper();
        productDto = objectMapper.readValue(product, ProductDto.class);

        return productDto;
    }
}
