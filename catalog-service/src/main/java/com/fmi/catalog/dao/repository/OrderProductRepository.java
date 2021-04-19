package com.fmi.catalog.dao.repository;

import com.fmi.catalog.dao.entity.OrderProductEntity;
import com.fmi.catalog.dao.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, OrderProductId> {
}
