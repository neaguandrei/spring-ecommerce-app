package com.fmi.user.service;

import com.fmi.dao.entity.UserEntity;
import com.fmi.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapperService {

    private final ModelMapper modelMapper;

    public UserEntity convertCustomerDtoToCustomer(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);

    }

    public UserDto convertCustomerToCustomerDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
}
