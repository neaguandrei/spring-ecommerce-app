package com.fmi.user.dao.repository;


import com.fmi.user.dao.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    Optional<AddressEntity> findByUserId(Long userId);
}
