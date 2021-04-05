package com.fmi.user.service;

import com.fmi.common.exception.BadRequestException;
import com.fmi.common.exception.NotFoundException;
import com.fmi.dao.entity.AddressEntity;
import com.fmi.dao.entity.UserEntity;
import com.fmi.dao.repository.UserRepository;
import com.fmi.user.mapper.UserMapper;
import com.fmi.user.model.User;
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

    private final UserMapper userMapper;

    public void save(User user) throws BadRequestException {
        validateCreateUniqueFields(user);

        final UserEntity userEntity = userMapper.mapToEntity(user);
        final AddressEntity addressEntity = userMapper.mapToEntity(user.getAddress());
        userEntity.setAddress(addressEntity);
        addressEntity.setUser(userEntity);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(userEntity);
    }

    public void update(Long id, User user) throws BadRequestException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (optionalUserEntity.isPresent()) {
            final UserEntity existingUser = optionalUserEntity.get();
            validateUpdateFields(user, existingUser);

            final UserEntity updatedUser = userMapper.mapToEntity(user);
            final AddressEntity updatedAddress = userMapper.mapToEntity(user.getAddress());

            userMapper.mapToUpdatedEntity(updatedUser, existingUser, user.getOldPassword());
            userMapper.mapToUpdatedEntity(updatedAddress, existingUser.getAddress());
        }
    }

    public User getByEmail(String email) throws NotFoundException {
        return userRepository.findByEmail(email).map(userMapper::mapFromEntity)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public void delete(Long userId) throws NotFoundException {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        userRepository.deleteById(userId);
    }

    private void validateCreateUniqueFields(User user) throws BadRequestException {
        boolean isEmailExisting = userRepository.findByEmail(user.getEmail()).isPresent();
        if (isEmailExisting) {
            throw new BadRequestException("E-mail already exists!");
        }

        boolean isPhoneExisting = userRepository.findByPhone(user.getPhone()).isPresent();
        if (isPhoneExisting) {
            throw new BadRequestException("Phone already exists!");
        }
    }

    private void validateUpdateFields(User user, UserEntity existingEntity) throws BadRequestException {
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
