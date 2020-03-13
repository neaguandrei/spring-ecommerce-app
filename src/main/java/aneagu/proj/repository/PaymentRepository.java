package aneagu.proj.repository;

import aneagu.proj.models.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Iterable<Payment> findAllByCustomer_Id(Long id);
}
