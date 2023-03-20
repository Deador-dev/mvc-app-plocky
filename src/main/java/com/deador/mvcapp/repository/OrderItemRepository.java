package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.OrderItem;
import com.deador.mvcapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderUser(User user);
    List<OrderItem> findAllByOrderId(Long id);
}