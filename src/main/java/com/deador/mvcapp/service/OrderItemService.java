package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.OrderItem;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {
    List<OrderItem> getAllOrderItems();

    List<OrderItem> getAllOrderItemsReverseByUser(User user);
}
