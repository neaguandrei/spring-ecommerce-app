package aneagu.proj.repository;

import aneagu.proj.models.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.customer.id = :id")
    Iterable<Order> findAllByCustomerId(Long id);
}
