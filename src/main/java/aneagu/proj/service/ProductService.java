package aneagu.proj.service;

import aneagu.proj.models.dto.ProductDto;
import aneagu.proj.models.enums.ProductCategory;
import aneagu.proj.models.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDto> getProducts(String searchKey, ProductCategory productCategory,  Pageable pageable)
            throws NotFoundException;
}
