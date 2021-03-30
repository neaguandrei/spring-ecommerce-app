package com.fmi.dao.repository;

import com.fmi.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.order.id = :id ")
    Iterable<PaymentEntity> findAllByOrderId(Long id);
}
