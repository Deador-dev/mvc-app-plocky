package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {
    boolean createOrderItems(List<CartItem> allCartItems, Order order);

    List<OrderItem> getAllOrderItems();

    List<OrderItem> getAllOrderItemsReverseByUser(User user);
}
