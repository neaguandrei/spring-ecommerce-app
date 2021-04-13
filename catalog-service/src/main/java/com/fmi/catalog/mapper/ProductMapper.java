package com.fmi.catalog.mapper;

import com.fmi.api.catalog.ProductDto;
import com.fmi.dao.entity.ProductEntity;
import com.fmi.catalog.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
}
