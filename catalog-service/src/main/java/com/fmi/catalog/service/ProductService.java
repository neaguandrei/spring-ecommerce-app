package com.fmi.catalog.service;

import com.fmi.catalog.model.enums.BucketName;
import com.fmi.catalog.service.aws.FileStore;
import com.fmi.common.exception.NotFoundException;
import com.fmi.catalog.mapper.ProductMapper;
import com.fmi.catalog.model.Product;
import com.fmi.catalog.dao.entity.ProductEntity;
import com.fmi.catalog.dao.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.apache.http.entity.ContentType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final FileStore fileStore;

    public Page<Product> getProducts(String searchKey, ProductEntity.ProductCategory productCategory, Pageable pageable) {
        if (Objects.nonNull(searchKey) && !searchKey.isEmpty()) {
            if (productCategory != null && productCategory != ProductEntity.ProductCategory.NONE) {
                return productRepository.findAllByNameAndCategory(searchKey, productCategory, pageable)
                        .map(productMapper::mapFromEntity);
            }
            return productRepository.findAllByName(searchKey, pageable).map(productMapper::mapFromEntity);
        }
        return productRepository.findAll(pageable).map(productMapper::mapFromEntity);
    }

    public Product getProductById(Long id) throws NotFoundException {
        Optional<Product> optional = productRepository.findById(id).map(productMapper::mapFromEntity);
        if (!optional.isPresent()) {
            throw new NotFoundException("Product doesn't exist!");
        }

        return optional.get();
    }

    public List<Product> getProducts(List<Long> productIds) {
        return productRepository.findAllByIdIn(productIds).stream().map(productMapper::mapFromEntity).collect(Collectors.toList());
    }

    public void save(Product product, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }

        if (!Arrays.asList(IMAGE_PNG.getMimeType(),
                IMAGE_BMP.getMimeType(),
                IMAGE_GIF.getMimeType(),
                IMAGE_JPEG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File uploaded is not an image");
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.PRODUCT_IMAGE.getName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

        ProductEntity productEntity = productMapper.mapToEntity(product);
        productEntity.setImagePath(path);
        productEntity.setImageFileName(fileName);

        productRepository.save(productEntity);
    }

    public byte[] downloadProductImage(Long id) throws NotFoundException {
        Product product = productRepository.findById(id).map(productMapper::mapFromEntity).orElseThrow(() -> new NotFoundException("Image for product not found."));
        return fileStore.download(product.getImagePath(), product.getImageFileName());
    }
}
