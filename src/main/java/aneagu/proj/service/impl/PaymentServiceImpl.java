package aneagu.proj.service.impl;

import aneagu.proj.models.domain.Payment;
import aneagu.proj.models.dto.PaymentDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.CustomerRepository;
import aneagu.proj.repository.PaymentRepository;
import aneagu.proj.service.MapperService;
import aneagu.proj.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    private final MapperService mapperService;

    private final CustomerRepository customerRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MapperService mapperService, CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.mapperService = mapperService;
        this.customerRepository = customerRepository;
    }

    @Override
    public void save(PaymentDto object) {
        paymentRepository.save(mapperService.convertPaymentDtoToPayment(object));
    }

    @Override
    public PaymentDto get(Long id) throws NotFoundException {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (!optionalPayment.isPresent()) {
            throw new NotFoundException("Payment not found!");
        }

        return mapperService.convertPaymentToPaymentDto(optionalPayment.get());
    }

    @Override
    public List<PaymentDto> getPaymentsForUserId(Long userId) throws NotFoundException {
        if (!customerRepository.findById(userId).isPresent()) {
            throw new NotFoundException("Customer doesn't exist");
        }
        List<PaymentDto> paymentDtos = new ArrayList<>();
        paymentRepository.findAllByCustomer_Id(userId)
                .forEach(payment -> paymentDtos.add(mapperService.convertPaymentToPaymentDto(payment)));

        return paymentDtos;
    }

}
