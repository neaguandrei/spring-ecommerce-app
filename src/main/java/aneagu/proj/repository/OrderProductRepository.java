package aneagu.proj.repository;

import aneagu.proj.domain.Order;
import aneagu.proj.domain.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<Order, OrderProductId> {
}
