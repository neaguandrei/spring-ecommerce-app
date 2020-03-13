package aneagu.proj.service;

import aneagu.proj.models.exception.NotFoundException;

public interface CrudService<T> {

    void delete(T object) throws NotFoundException;

    void save(T object);

    void update(Long id, T object) throws NotFoundException;

    T get(Long id) throws NotFoundException;
}
