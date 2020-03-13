package aneagu.proj.service.impl;

import aneagu.proj.models.dto.ProductDto;
import aneagu.proj.models.enums.ProductCategory;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public List<ProductDto> getProductsBySearchKey(String searchKey) throws NotFoundException {
        return null;
    }

    @Override
    public List<ProductDto> getProductsByCategory(ProductCategory productCategory) throws NotFoundException {
        return null;
    }

    @Override
    public List<ProductDto> getProductsByProductLine(Long productLineId) throws NotFoundException {
        return null;
    }

}
