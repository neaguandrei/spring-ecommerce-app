package aneagu.proj.service.impl;

import aneagu.proj.models.domain.Customer;
import aneagu.proj.models.dto.CustomerDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.CustomerRepository;
import aneagu.proj.service.CustomerService;
import aneagu.proj.service.MapperService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_NOT_FOUND = "Customer not found";

    private final CustomerRepository customerRepository;

    private final MapperService mapperService;

    public CustomerServiceImpl(CustomerRepository customerRepository, MapperService mapperService) {
        this.customerRepository = customerRepository;
        this.mapperService = mapperService;
    }

    @Override
    public void delete(CustomerDto object) throws NotFoundException {
        Optional<Customer> address = customerRepository.findById(object.getId());
        if (!address.isPresent()) {
            throw new NotFoundException(CUSTOMER_NOT_FOUND);
        }
        customerRepository.delete(mapperService.convertCustomerDtoToCustomer(object));
    }

    @Override
    public void save(CustomerDto object) {
        customerRepository.save(mapperService.convertCustomerDtoToCustomer(object));
    }

    @Override
    public void update(Long id, CustomerDto object) {
        if (object == null) {
            throw new IllegalArgumentException("Object can't be null!");
        }
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = mapperService.convertCustomerDtoToCustomer(object);
            customer.setId(id);
            customerRepository.save(mapperService.convertCustomerDtoToCustomer(object));
        }
    }

    @Override
    public CustomerDto get(Long id) throws NotFoundException {
        return mapperService.convertCustomerToCustomerDto(customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND)));
    }
}
