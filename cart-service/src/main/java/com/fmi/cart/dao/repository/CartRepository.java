package com.fmi.cart.dao.repository;

import com.fmi.cart.dao.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

}
