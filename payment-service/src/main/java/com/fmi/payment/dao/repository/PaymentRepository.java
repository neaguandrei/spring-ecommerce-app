package com.fmi.payment.dao.repository;



import com.fmi.payment.dao.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Iterable<PaymentEntity> findAllByUserId(Long userId);
}
