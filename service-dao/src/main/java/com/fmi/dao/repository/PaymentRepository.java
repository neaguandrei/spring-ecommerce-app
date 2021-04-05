package com.fmi.dao.repository;

import com.fmi.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Iterable<PaymentEntity> findAllByOrderId(Long id);
}
