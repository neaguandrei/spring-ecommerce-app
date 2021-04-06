package com.fmi.dao.repository;

import com.fmi.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Iterable<PaymentEntity> findByOrderIdIn(List<Long> orderIds);
}
