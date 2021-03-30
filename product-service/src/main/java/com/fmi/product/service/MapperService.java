package com.fmi.product.service;

import com.fmi.dao.entity.ProductEntity;
import com.fmi.product.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    public ProductEntity convertProductDtoToProduct(ProductDto productDto) {
        return modelMapper.map(productDto, ProductEntity.class);
    }

    public ProductDto convertProductToProductDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDto.class);
    }
}
