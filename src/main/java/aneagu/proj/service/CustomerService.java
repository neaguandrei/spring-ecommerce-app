package aneagu.proj.service;

import aneagu.proj.models.dto.CustomerDto;
import aneagu.proj.models.exception.BadRequestException;
import aneagu.proj.models.exception.NotFoundException;

public interface CustomerService {
    void delete(Long id) throws NotFoundException;

    Long save(CustomerDto dto) throws BadRequestException;

    void update(Long id, CustomerDto dto) throws NotFoundException;

    CustomerDto get(Long id) throws NotFoundException;
}
