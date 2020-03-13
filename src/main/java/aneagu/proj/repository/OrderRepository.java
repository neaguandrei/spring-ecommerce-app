package aneagu.proj.repository;

import aneagu.proj.models.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Iterable<Order> findAllByCustomer_Id(Long id);
}
