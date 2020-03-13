package aneagu.proj.service;

import aneagu.proj.models.dto.ProductDto;
import aneagu.proj.models.enums.ProductCategory;
import aneagu.proj.models.exception.NotFoundException;

import java.util.List;

public interface ProductService {
    List<ProductDto> getProductsBySearchKey(String searchKey) throws NotFoundException;

    List<ProductDto> getProductsByCategory(ProductCategory productCategory) throws NotFoundException;

    List<ProductDto> getProductsByProductLine(Long productLineId) throws NotFoundException;
}
