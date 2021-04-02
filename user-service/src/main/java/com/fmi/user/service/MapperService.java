package com.fmi.user.service;

import com.fmi.dao.entity.AddressEntity;
import com.fmi.dao.entity.UserEntity;
import com.fmi.user.dto.AddressDto;
import com.fmi.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserEntity mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);

    }

    public UserDto mapToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    public AddressEntity mapToEntity(AddressDto addressDto) {
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
