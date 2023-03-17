package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.Order;
import com.deador.mvcapp.repository.OrderRepository;
import com.deador.mvcapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
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
        Optional<Order> optionalOrder = getOptionalOrderById(id);
        if(optionalOrder.isEmpty()){
            // TODO: 17.03.2023 exception ORDER_NOT_FOUND_BY_ID
        }
        return optionalOrder.orElseThrow();
    }

    @Override
    public boolean isOrderExistsById(Long id) {
        return orderRepository.existsById(id);
    }

    private Optional<Order> getOptionalOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
