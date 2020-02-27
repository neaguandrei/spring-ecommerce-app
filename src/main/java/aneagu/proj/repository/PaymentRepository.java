package aneagu.proj.repository;

import aneagu.proj.domain.Payment;
import aneagu.proj.domain.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
