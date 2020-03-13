package aneagu.proj.service.impl;

import aneagu.proj.models.domain.Address;
import aneagu.proj.models.dto.AddressDto;
import aneagu.proj.models.exception.NotFoundException;
import aneagu.proj.repository.AddressRepository;
import aneagu.proj.service.AddressService;
import aneagu.proj.service.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    public static final String ADDRESS_NOT_FOUND = "Address not found!";

    private final AddressRepository addressRepository;

    private final MapperService mapperService;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, MapperService mapperService) {
        this.addressRepository = addressRepository;
        this.mapperService = mapperService;
    }

    @Override
    public void delete(AddressDto object) throws NotFoundException {
        Optional<Address> address = addressRepository.findById(object.getId());
        if (!address.isPresent()) {
            throw new NotFoundException(ADDRESS_NOT_FOUND);
        }
        addressRepository.delete(mapperService.convertAddressDtoToAddress(object));
    }

    @Override
    public void save(AddressDto object) {
        addressRepository.save(mapperService.convertAddressDtoToAddress(object));
    }

    @Override
    public void update(Long id, AddressDto object) {
        if (object == null) {
            throw new IllegalArgumentException("Object can't be null!");
        }
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            Address address = mapperService.convertAddressDtoToAddress(object);
            address.setId(id);
            addressRepository.save(mapperService.convertAddressDtoToAddress(object));
        }
    }

    @Override
    public AddressDto get(Long id) throws NotFoundException {
        return mapperService.convertAddressToAddressDto(addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ADDRESS_NOT_FOUND)));
    }
}
