package com.fmi.dao.repository;

import com.fmi.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.order.internalId = :id ")
    Iterable<PaymentEntity> findAllByOrderId(String id);

    Optional<PaymentEntity> findByInternalId(String internalId);
}
