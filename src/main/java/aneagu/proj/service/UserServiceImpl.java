package aneagu.proj.service;

import aneagu.proj.models.domain.Address;
import aneagu.proj.models.domain.User;
import aneagu.proj.models.dto.UserDto;
import aneagu.proj.models.exception.BadRequestException;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.AddressRepository;
import aneagu.proj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String CUSTOMER_NOT_FOUND = "Customer not found";

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    private final MapperService mapperService;

    @Override
    public void delete(Long id) throws NotFoundException {
        Optional<User> address = userRepository.findById(id);
        if (!address.isPresent()) {
            throw new NotFoundException(CUSTOMER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Long save(UserDto object) throws BadRequestException {
        boolean isEmailExisting = userRepository.findByEmail(object.getEmail()).isPresent();
        boolean isPhoneExisting = userRepository.findByPhone(object.getPhone()).isPresent();
        if (isEmailExisting) {
            throw new BadRequestException("E-mail already exists!");
        }

        if (isPhoneExisting) {
            throw new BadRequestException("Phone already exists!");
        }

        Address address = addressRepository.save(mapperService.convertAddressDtoToAddress(object.getAddress()));
        User user = mapperService.convertCustomerDtoToCustomer(object);
        user.setPassword(passwordEncoder.encode(object.getPassword()));
        user.setAddress(address);
        return userRepository.save(user).getId();
    }

    @Override
    public void update(Long id, UserDto object) {
        if (object == null) {
            throw new IllegalArgumentException("Object can't be null!");
        }

        Optional<User> optionalCustomer = userRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            User user = mapperService.convertCustomerDtoToCustomer(object);
            if (user.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(object.getPassword()));
            }
            user.setId(id);
            updateAddress(id, user);
            userRepository.save(mapperService.convertCustomerDtoToCustomer(object));
        }
    }

    @Override
    public UserDto get(Long id) throws NotFoundException {
        return mapperService.convertCustomerToCustomerDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND)));
    }

    private void updateAddress(Long id, User user) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        optionalAddress.ifPresent(address -> {
            addressRepository.save(address);
            user.setAddress(address);
        });
    }
}
