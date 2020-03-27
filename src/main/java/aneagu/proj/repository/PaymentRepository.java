package aneagu.proj.repository;

import aneagu.proj.models.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.user.id = :id ")
    Iterable<Payment> findAllByCustomerId(Long id);
}
