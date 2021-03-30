package com.fmi.dao.repository;


import com.fmi.dao.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findAllByName(String name, Pageable pageable);

    Page<ProductEntity> findAllByNameAndCategory(String name, ProductEntity.ProductCategory category, Pageable pageable);

}
