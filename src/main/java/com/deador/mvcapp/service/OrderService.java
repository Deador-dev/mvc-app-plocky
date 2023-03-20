package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Order;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.entity.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    boolean createOrder(User user, OrderDTO orderDTO);

    List<Order> getAllOrders();

    Order getOrderById(Long id);

    List<Order> getAllOrdersByUserId(Long id);

    List<Order> getAllOrdersReverseByUserId(Long id);

    boolean isOrderExistsById(Long id);

    boolean changeDeliveryStatusByOrderId(Long id, String deliveryStatus);
}
