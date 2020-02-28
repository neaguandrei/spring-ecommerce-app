package aneagu.proj.repository;

import aneagu.proj.models.domain.Order;
import aneagu.proj.models.domain.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<Order, OrderProductId> {
}
