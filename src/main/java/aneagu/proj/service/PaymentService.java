package aneagu.proj.service;

import aneagu.proj.models.dto.PaymentDto;
import aneagu.proj.models.exception.NotFoundException;

import java.util.List;

public interface PaymentService {
    void save(PaymentDto object);

    PaymentDto get(Long id) throws NotFoundException;

    List<PaymentDto> getPaymentsForUserId(Long userId) throws NotFoundException;
}
