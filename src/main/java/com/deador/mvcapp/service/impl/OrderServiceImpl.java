package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.Order;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.repository.OrderRepository;
import com.deador.mvcapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String ORDER_CREATING_ERROR = "Order isn't created";
    private static final String ORDER_UPDATING_ERROR = "Can't update order by id: %s";
    private static final String ORDER_ALREADY_EXIST = "Order already exist with name %s";
    private static final String ORDER_NOT_FOUND_BY_ID = "Order not found by id: %s";
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new NotExistException(String.format(ORDER_NOT_FOUND_BY_ID, id));
        }

        return optionalOrder.get();
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long id) {
        return orderRepository.findAllByUserId(id);
    }

    @Override
    public List<Order> getAllOrdersReverseByUserId(Long id) {
        List<Order> orderList = getAllOrdersByUserId(id);
        Collections.reverse(orderList);

        return orderList;
    }

    @Override
    public boolean isOrderExistsById(Long id) {
        return orderRepository.existsById(id);
    }

}
