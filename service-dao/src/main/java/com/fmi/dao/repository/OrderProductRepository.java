package com.fmi.dao.repository;

import com.fmi.dao.entity.OrderProductEntity;
import com.fmi.dao.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, OrderProductId> {
}
