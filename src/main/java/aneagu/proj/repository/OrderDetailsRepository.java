package aneagu.proj.repository;

import aneagu.proj.models.domain.OrderProduct;
import aneagu.proj.models.domain.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
