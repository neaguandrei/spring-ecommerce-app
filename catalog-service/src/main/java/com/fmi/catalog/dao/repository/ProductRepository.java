package com.fmi.catalog.dao.repository;


import com.fmi.catalog.dao.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByIdIn(List<Long> ids);

    Page<ProductEntity> findAllByName(String name, Pageable pageable);

    Page<ProductEntity> findAllByNameAndCategory(String name, ProductEntity.ProductCategory category, Pageable pageable);

}
