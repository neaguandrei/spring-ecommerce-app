package aneagu.proj.service;

import aneagu.proj.models.dto.AddressDto;

import java.util.Optional;

public interface AddressService {

    void delete(AddressDto addressDto);

    void save(AddressDto addressDto);
    
    Optional<AddressDto> get();
}
