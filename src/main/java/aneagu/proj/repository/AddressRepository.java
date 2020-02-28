package aneagu.proj.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import aneagu.proj.models.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
