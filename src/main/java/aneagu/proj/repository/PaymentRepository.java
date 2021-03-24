package aneagu.proj.repository;

import aneagu.proj.models.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Query("SELECT p FROM PaymentEntity p WHERE p.user.id = :id ")
    Iterable<PaymentEntity> findAllByCustomerId(Long id);
}
