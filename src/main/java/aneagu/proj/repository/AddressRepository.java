package aneagu.proj.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import aneagu.proj.models.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
