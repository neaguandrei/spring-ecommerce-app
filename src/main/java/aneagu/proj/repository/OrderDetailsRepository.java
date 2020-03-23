package aneagu.proj.repository;

import aneagu.proj.models.domain.OrderDetails;
import aneagu.proj.models.domain.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsId> {

    Iterable<OrderDetails> findByOrder_Id(Long orderId);
}
