package aneagu.proj.service;

import aneagu.proj.models.domain.Address;
import aneagu.proj.models.domain.Customer;
import aneagu.proj.models.dto.CustomerDto;
import aneagu.proj.models.exception.BadRequestException;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.AddressRepository;
import aneagu.proj.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_NOT_FOUND = "Customer not found";

    private final CustomerRepository customerRepository;

    private final AddressRepository addressRepository;

    private final MapperService mapperService;

    @Override
    public void delete(Long id) throws NotFoundException {
        Optional<Customer> address = customerRepository.findById(id);
        if (!address.isPresent()) {
            throw new NotFoundException(CUSTOMER_NOT_FOUND);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public Long save(CustomerDto object) throws BadRequestException {
        boolean isEmailExisting = customerRepository.findByEmail(object.getEmail()).isPresent();
        boolean isPhoneExisting = customerRepository.findByPhone(object.getPhone()).isPresent();
        if (isEmailExisting || isPhoneExisting) {
            throw new BadRequestException("Customer e-mail already exists!");
        }

        Address address = addressRepository.save(mapperService.convertAddressDtoToAddress(object.getAddress()));
        Customer customer = mapperService.convertCustomerDtoToCustomer(object);
        customer.setAddress(address);
        return customerRepository.save(customer).getId();
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
            updateAddress(id, customer);
            customerRepository.save(mapperService.convertCustomerDtoToCustomer(object));
        }
    }

    @Override
    public CustomerDto get(Long id) throws NotFoundException {
        return mapperService.convertCustomerToCustomerDto(customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND)));
    }

    private void updateAddress(Long id, Customer customer) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        optionalAddress.ifPresent(address -> {
            addressRepository.save(address);
            customer.setAddress(address);
        });
    }
}
