package com.fmi.user.mapper;

import com.fmi.api.user.UserDto;
import com.fmi.common.exception.BadRequestException;
import com.fmi.user.dao.entity.AddressEntity;
import com.fmi.user.dao.entity.RoleEntity;
import com.fmi.user.dao.entity.UserEntity;
import com.fmi.user.model.Address;
import com.fmi.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserDto mapToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User mapFromDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserEntity mapToEntity(User user) {
        modelMapper.typeMap(User.class, UserEntity.class).addMappings(mapping -> mapping.skip(UserEntity::setRoles));
        return modelMapper.map(user, UserEntity.class);
    }

    public User mapFromEntity(UserEntity entity) {
        modelMapper.typeMap(UserEntity.class, User.class).addMappings(mapping -> mapping.skip(User::setRoles));
        User user = modelMapper.map(entity, User.class);
        user.setRoles(entity.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()));

        return user;
    }

    public AddressEntity mapToEntity(Address addressDto) {
        return modelMapper.map(addressDto, AddressEntity.class);

    }

    public void mapToUpdatedEntity(UserEntity updatedEntity, UserEntity existingEntity, String newPassword) throws BadRequestException {
        if (!passwordEncoder.matches(updatedEntity.getPassword(), existingEntity.getPassword())) {
            throw new BadRequestException("Please enter a correct password before proceeding with the update of your profile.");
        }

        if (updatedEntity.getPassword() != null && newPassword != null) {
            existingEntity.setPassword(passwordEncoder.encode(newPassword));
        }
        existingEntity.setEmail(updatedEntity.getEmail());
        existingEntity.setFirstName(updatedEntity.getFirstName());
        existingEntity.setLastName(updatedEntity.getLastName());
        existingEntity.setPhone(updatedEntity.getPhone());
    }

    public void mapToUpdatedEntity(AddressEntity updatedEntity, AddressEntity existingEntity) {
        existingEntity.setAddressOne(updatedEntity.getAddressOne());
        existingEntity.setAddressTwo(updatedEntity.getAddressTwo());
        existingEntity.setCity(updatedEntity.getCity());
        existingEntity.setCountry(updatedEntity.getCountry());
        existingEntity.setPostalCode(updatedEntity.getPostalCode());
        existingEntity.setState(updatedEntity.getState());
    }
}
