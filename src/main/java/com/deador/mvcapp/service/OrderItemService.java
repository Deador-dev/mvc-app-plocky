package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.CartItem;
import com.deador.mvcapp.entity.Order;
import com.deador.mvcapp.entity.OrderItem;
import com.deador.mvcapp.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {
    boolean createOrderItems(List<CartItem> allCartItems, Order order);

    List<OrderItem> getAllOrderItems();

    List<OrderItem> getAllOrderItemsByOrderId(Long id);

    List<OrderItem> getAllOrderItemsReverseByUser(User user);
}
