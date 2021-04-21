package com.fmi.catalog.controller;


import com.fmi.api.catalog.ProductDto;
import com.fmi.api.catalog.ProductsRequestResource;
import com.fmi.api.catalog.ProductsResponseResource;
import com.fmi.common.exception.NotFoundException;
import com.fmi.common.validation.OneOf;
import com.fmi.catalog.dao.entity.ProductEntity;
import com.fmi.catalog.mapper.ProductMapper;
import com.fmi.catalog.model.enums.ProductCategory;
import com.fmi.catalog.service.ProductService;
import com.fmi.security.annotation.PreAuthorizeAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class RestProductController {

    private final ProductService productService;

    private final ProductMapper productMapper;

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(value = "product_id") Long productId) throws NotFoundException {
        return ResponseEntity.ok(productMapper.mapToDto(productService.getProductById(productId)));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                        @RequestParam(name = "sort", defaultValue = "DESC") String sort,
                                                        @RequestParam(name = "sorted_param", defaultValue = "quantity") String sortedParam,
                                                        @RequestParam(name = "search_key", defaultValue = "") String searchKey,
                                                        @RequestParam(name = "category", defaultValue = "NONE")
                                                        @OneOf(enumClass = ProductCategory.class, message = "Category isn't of correct type.") String productCategory) {
        return ResponseEntity.ok(productMapper.mapToDto(productService.getProducts(searchKey, ProductEntity.ProductCategory.valueOf(productCategory), PageRequest.of(page, size, Sort.Direction.valueOf(sort), sortedParam))));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<ProductsResponseResource> getProductsList(@RequestBody @Valid ProductsRequestResource requestResource) {
        return ResponseEntity.ok(productMapper.mapToResource(productService.getProducts(requestResource.getProductIds())));
    }

    @PreAuthorizeAdmin
    @PostMapping("/product")
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductDto productDto) {
        productService.save(productMapper.mapFromDto(productDto));
        return ResponseEntity.ok().build();
    }
}
