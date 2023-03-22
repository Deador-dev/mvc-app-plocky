package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.OrderItem;
import com.deador.mvcapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAll();

    List<OrderItem> findAllByOrderUser(User user);

    List<OrderItem> findAllByOrderId(Long id);
}