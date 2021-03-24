package aneagu.proj.service;

import aneagu.proj.models.entity.PaymentEntity;
import aneagu.proj.models.dto.PaymentDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.UserRepository;
import aneagu.proj.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService  {

    private final PaymentRepository paymentRepository;

    private final UserRepository userRepository;

    private final MapperService mapperService;

    public void save(PaymentDto object) {
        paymentRepository.save(mapperService.convertPaymentDtoToPayment(object));
    }

    public PaymentDto get(Long id) throws NotFoundException {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent()) {
            throw new NotFoundException("Payment not found!");
        }

        return mapperService.convertPaymentToPaymentDto(optionalPayment.get());
    }

    public List<PaymentDto> getPaymentsForUserId(Long userId) throws NotFoundException {
        if (!userRepository.findById(userId).isPresent()) {
            throw new NotFoundException("Customer doesn't exist");
        }
        List<PaymentDto> paymentDtos = new ArrayList<>();
        paymentRepository.findAllByCustomerId(userId)
                .forEach(payment -> paymentDtos.add(mapperService.convertPaymentToPaymentDto(payment)));

        return paymentDtos;
    }

}
