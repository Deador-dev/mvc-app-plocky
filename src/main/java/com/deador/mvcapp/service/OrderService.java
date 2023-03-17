package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id);

    boolean isOrderExistsById(Long id);
}
