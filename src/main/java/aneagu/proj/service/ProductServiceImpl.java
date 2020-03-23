package aneagu.proj.service;

import aneagu.proj.models.dto.ProductDto;
import aneagu.proj.models.enums.ProductCategory;
import aneagu.proj.repository.ProductRepository;
import aneagu.proj.service.MapperService;
import aneagu.proj.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final MapperService mapperService;

    @Override
    public Page<ProductDto> getProducts(String searchKey, ProductCategory productCategory, Pageable pageable) {
        if (Objects.nonNull(searchKey) && !searchKey.isEmpty()) {
            if (productCategory != null && productCategory != ProductCategory.NONE) {
                return productRepository.findAllByNameAndCategory(searchKey, productCategory, pageable)
                        .map(mapperService::convertProductToProductDto);
            }
            return productRepository.findAllByName(searchKey, pageable)
                    .map(mapperService::convertProductToProductDto);
        }
        return productRepository.findAll(pageable)
                .map(mapperService::convertProductToProductDto);
    }
}
