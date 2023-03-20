package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Order;
import org.aspectj.weaver.ast.Or;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order getOrderById(Long id);

    List<Order> getAllOrdersByUserId(Long id);
    List<Order> getAllOrdersReverseByUserId(Long id);

    boolean isOrderExistsById(Long id);
}
