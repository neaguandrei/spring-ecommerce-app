package com.fmi.user.service;

import com.fmi.dao.entity.AddressEntity;
import com.fmi.dao.entity.UserEntity;
import com.fmi.user.dto.AddressDto;
import com.fmi.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    public AddressEntity convertAddressDtoToAddress(AddressDto addressDto) {
        return modelMapper.map(addressDto, AddressEntity.class);
    }

    public AddressDto convertAddressToAddressDto(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity, AddressDto.class);
    }

    public UserEntity convertCustomerDtoToCustomer(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);

    }

    public UserDto convertCustomerToCustomerDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
}
