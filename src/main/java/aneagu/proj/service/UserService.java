package aneagu.proj.service;

import aneagu.proj.models.entity.AddressEntity;
import aneagu.proj.models.entity.UserEntity;
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
public class UserService {

    private static final String CUSTOMER_NOT_FOUND = "Customer not found";

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final PasswordEncoder passwordEncoder;

    private final MapperService mapperService;

    public void delete(Long id) throws NotFoundException {
        Optional<UserEntity> address = userRepository.findById(id);
        if (!address.isPresent()) {
            throw new NotFoundException(CUSTOMER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    public Long save(UserDto object) throws BadRequestException {
        boolean isEmailExisting = userRepository.findByEmail(object.getEmail()).isPresent();
        boolean isPhoneExisting = userRepository.findByPhone(object.getPhone()).isPresent();
        if (isEmailExisting) {
            throw new BadRequestException("E-mail already exists!");
        }

        if (isPhoneExisting) {
            throw new BadRequestException("Phone already exists!");
        }

        AddressEntity addressEntity = addressRepository.save(mapperService.convertAddressDtoToAddress(object.getAddress()));
        UserEntity userEntity = mapperService.convertCustomerDtoToCustomer(object);
        userEntity.setPassword(passwordEncoder.encode(object.getPassword()));
        userEntity.setAddress(addressEntity);
        return userRepository.save(userEntity).getId();
    }

    public void update(Long id, UserDto object) {
        if (object == null) {
            throw new IllegalArgumentException("Object can't be null!");
        }

        Optional<UserEntity> optionalCustomer = userRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            UserEntity userEntity = mapperService.convertCustomerDtoToCustomer(object);
            if (userEntity.getPassword() != null) {
                userEntity.setPassword(passwordEncoder.encode(object.getPassword()));
            }
            userEntity.setId(id);
            updateAddress(id, userEntity);
            userRepository.save(mapperService.convertCustomerDtoToCustomer(object));
        }
    }

    public UserDto get(Long id) throws NotFoundException {
        return mapperService.convertCustomerToCustomerDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CUSTOMER_NOT_FOUND)));
    }

    private void updateAddress(Long id, UserEntity userEntity) {
        Optional<AddressEntity> optionalAddress = addressRepository.findById(id);
        optionalAddress.ifPresent(address -> {
            addressRepository.save(address);
            userEntity.setAddress(address);
        });
    }
}
