package com.fmi.product.service;

import com.fmi.common.exception.NotFoundException;
import com.fmi.product.dto.ProductDto;
import com.fmi.dao.entity.ProductEntity;
import com.fmi.dao.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    private final MapperService mapperService;

    public Page<ProductDto> getProducts(String searchKey, ProductEntity.ProductCategory productCategory, Pageable pageable) {
        if (Objects.nonNull(searchKey) && !searchKey.isEmpty()) {
            if (productCategory != null && productCategory != ProductEntity.ProductCategory.NONE) {
                return productRepository.findAllByNameAndCategory(searchKey, productCategory, pageable)
                        .map(mapperService::convertProductToProductDto);
            }
            return productRepository.findAllByName(searchKey, pageable)
                    .map(mapperService::convertProductToProductDto);
        }
        return productRepository.findAll(pageable)
                .map(mapperService::convertProductToProductDto);
    }

    public ProductDto getProductByInternalId(String internalId) throws NotFoundException {
        Optional<ProductDto> optional = productRepository.findByInternalId(internalId).map(mapperService::convertProductToProductDto);
        if (!optional.isPresent()) {
            throw new NotFoundException("Product doesn't exist!");
        }

        return optional.get();
    }
}
