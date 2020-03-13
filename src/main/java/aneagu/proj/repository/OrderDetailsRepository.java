package aneagu.proj.repository;

import aneagu.proj.models.domain.OrderDetails;
import aneagu.proj.models.domain.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderProductId> {

}
