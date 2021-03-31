package com.fmi.product.controller;


import com.fmi.common.exception.NotFoundException;
import com.fmi.common.validation.OneOf;
import com.fmi.dao.entity.ProductEntity;
import com.fmi.product.dto.ProductDto;
import com.fmi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class RestProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) throws NotFoundException {
        return ResponseEntity.ok(productService.getProductByInternalId(productId));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                        @RequestParam(name = "sort", defaultValue = "DESC") String sort,
                                                        @RequestParam(name = "sortedParam", defaultValue = "quantityInStock") String sortedParam,
                                                        @RequestParam(name = "searchKey", defaultValue = "") String searchKey,
                                                        @RequestParam(name = "productCategory", defaultValue = "None")
                                                        @OneOf(enumClass = ProductDto.ProductCategory.class, message = "Category isn't of correct type.") String productCategory) {
        return ResponseEntity.ok(productService.getProducts(searchKey, ProductEntity.ProductCategory.valueOf(productCategory), PageRequest.of(page, size, Sort.Direction.valueOf(sort), sortedParam)));
    }

}
