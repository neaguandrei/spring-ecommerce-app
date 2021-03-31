package com.fmi.dao.repository;

import com.fmi.dao.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.user.internalId = :id")
    Page<OrderEntity> findAllByUserId(String id, Pageable pageable);

    Optional<OrderEntity> findByInternalId(String internalId);
}
