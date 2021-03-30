package com.fmi.product.controller;


import com.fmi.common.exception.NotFoundException;
import com.fmi.product.dto.ProductDto;
import com.fmi.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.fmi.dao.entity.ProductEntity.ProductCategory.fromValue;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable(value = "id") Long id) throws NotFoundException {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProducts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                        @RequestParam(name = "sort", defaultValue = "DESC") String sort,
                                                        @RequestParam(name = "sortedParam", defaultValue = "quantityInStock") String sortedParam,
                                                        @RequestParam(name = "searchKey", defaultValue = "") String searchKey,
                                                        @RequestParam(name = "productCategory", defaultValue = "None") String productCategory) {
        return ResponseEntity.ok(productService.getProducts(searchKey, fromValue(productCategory), PageRequest.of(page, size, Sort.Direction.valueOf(sort), sortedParam)));
    }

}
