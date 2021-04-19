package com.fmi.catalog.dao.repository;

import com.fmi.catalog.dao.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByUserId(Long id, Pageable pageable);
}
