package aneagu.proj.service;

import java.util.Optional;

public interface CrudService<T> {
    void delete(T object);

    void save(T object);

    void update(T object);

    Optional<T> get();
}
