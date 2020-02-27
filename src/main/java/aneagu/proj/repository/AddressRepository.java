package aneagu.proj.repository;

import aneagu.proj.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Order, Long> {
}
