package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAll();

    Optional<Order> findById(Long id);

    List<Order> findAllByUserId(Long id);

    boolean existsById(Long id);
}