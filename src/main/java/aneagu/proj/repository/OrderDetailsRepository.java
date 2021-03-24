package aneagu.proj.repository;

import aneagu.proj.models.entity.OrderDetailsEntity;
import aneagu.proj.models.entity.OrderDetailsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, OrderDetailsId> {

    Iterable<OrderDetailsEntity> findByOrder_Id(Long orderId);
}
