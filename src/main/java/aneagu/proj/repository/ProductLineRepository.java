package aneagu.proj.repository;

import aneagu.proj.domain.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineRepository extends JpaRepository<ProductLine, Long> {
}
