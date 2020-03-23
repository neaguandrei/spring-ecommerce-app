package aneagu.proj.repository;

import aneagu.proj.models.domain.Product;
import aneagu.proj.models.enums.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByName(String name, Pageable pageable);

    Page<Product> findAllByNameAndCategory(String name, ProductCategory category, Pageable pageable);

}
