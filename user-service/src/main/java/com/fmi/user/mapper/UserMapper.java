package com.fmi.user.mapper;

import com.fmi.api.user.UserDto;
import com.fmi.dao.entity.AddressEntity;
import com.fmi.dao.entity.UserEntity;
import com.fmi.user.model.Address;
import com.fmi.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserEntity mapToEntity(User userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    public UserDto mapToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User mapFromDto(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public User mapFromEntity(UserEntity entity) {
        return modelMapper.map(entity, User.class);
    }

    public AddressEntity mapToEntity(Address addressDto) {
        return modelMapper.map(addressDto, AddressEntity.class);

    }

    public void mapToUpdatedEntity(UserEntity updatedEntity, UserEntity existingEntity, String oldPassword) {
        if (updatedEntity.getPassword() != null && oldPassword != null && passwordEncoder.matches(oldPassword, existingEntity.getPassword())) {
            existingEntity.setPassword(passwordEncoder.encode(updatedEntity.getPassword()));
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
