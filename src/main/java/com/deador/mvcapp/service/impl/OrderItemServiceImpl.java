package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.OrderItem;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.repository.OrderItemRepository;
import com.deador.mvcapp.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getAllOrderItemsReverseByUser(User user) {
        List<OrderItem> orderItemListByUser = orderItemRepository.findByOrderUser(user);
        Collections.reverse(orderItemListByUser);

        return orderItemListByUser;
    }
}
