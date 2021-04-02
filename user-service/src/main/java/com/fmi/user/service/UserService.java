package com.fmi.user.service;

import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.user.dto.UserDto;
import com.fmi.dao.entity.AddressEntity;
import com.fmi.dao.entity.UserEntity;
import com.fmi.dao.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MapperService mapperService;

    public void save(UserDto user) throws BadRequestException {
        validateCreateUniqueFields(user);

        final UserEntity userEntity = mapperService.mapToEntity(user);
        final AddressEntity addressEntity = mapperService.mapToEntity(user.getAddress());
        userEntity.setAddress(addressEntity);
        addressEntity.setUser(userEntity);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
    }

    public void update(Long id, UserDto user) throws BadRequestException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            final UserEntity existingUser = optionalUserEntity.get();
            validateUpdateFields(user, existingUser);

            final UserEntity updatedUser = mapperService.mapToEntity(user);
            final AddressEntity updatedAddress = mapperService.mapToEntity(user.getAddress());

            mapperService.mapToUpdatedEntity(updatedUser, existingUser, user.getOldPassword());
            mapperService.mapToUpdatedEntity(updatedAddress, existingUser.getAddress());
        }
    }

    public UserDto getByEmail(String email) throws NotFoundException {
        return mapperService.mapToDto(userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND)));
    }

    public void delete(Long userId) throws NotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        userRepository.deleteById(userId);
    }

    private void validateCreateUniqueFields(UserDto user) throws BadRequestException {
        boolean isEmailExisting = userRepository.findByEmail(user.getEmail()).isPresent();
        if (isEmailExisting) {
            throw new BadRequestException("E-mail already exists!");
        }

        boolean isPhoneExisting = userRepository.findByPhone(user.getPhone()).isPresent();
        if (isPhoneExisting) {
            throw new BadRequestException("Phone already exists!");
        }
    }

    private void validateUpdateFields(UserDto user, UserEntity existingEntity) throws BadRequestException {
        if (!user.getEmail().equals(existingEntity.getEmail())) {
            boolean isEmailExisting = userRepository.findByEmail(user.getEmail()).isPresent();
            if (isEmailExisting) {
                throw new BadRequestException("E-mail already exists!");
            }
        }

        if (!user.getPhone().equals(existingEntity.getPhone())) {
            boolean isPhoneExisting = userRepository.findByPhone(user.getPhone()).isPresent();
            if (isPhoneExisting) {
                throw new BadRequestException("Phone already exists!");
            }
        }
    }
}
