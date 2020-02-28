package aneagu.proj.repository;

import aneagu.proj.models.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Order, Long> {
}
