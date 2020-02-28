package aneagu.proj.repository;

import aneagu.proj.models.domain.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineRepository extends JpaRepository<ProductLine, String> {
}
