package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}