package aneagu.proj.service;

import aneagu.proj.models.dto.UserDto;
import aneagu.proj.models.exception.BadRequestException;
import aneagu.proj.models.exception.NotFoundException;

public interface UserService {
    void delete(Long id) throws NotFoundException;

    Long save(UserDto dto) throws BadRequestException;

    void update(Long id, UserDto dto) throws NotFoundException;

    UserDto get(Long id) throws NotFoundException;

}
