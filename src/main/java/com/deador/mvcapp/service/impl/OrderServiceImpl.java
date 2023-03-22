package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.converter.DTOConverter;
import com.deador.mvcapp.entity.Order;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.entity.dto.OrderDTO;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.repository.OrderRepository;
import com.deador.mvcapp.service.CartItemService;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.OrderItemService;
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
    private final DTOConverter dtoConverter;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @Autowired
    public OrderServiceImpl(DTOConverter dtoConverter,
                            OrderRepository orderRepository,
                            OrderItemService orderItemService,
                            CartService cartService,
                            CartItemService cartItemService) {
        this.dtoConverter = dtoConverter;
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @Override
    public boolean createOrder(User user, OrderDTO orderDTO) {
        Order order = dtoConverter.convertToEntity(orderDTO, Order.class);

        order.setDeliveryStatus("Preparation");
        order.setTotalAmount(cartService.getCartPriceByUser(user));
        order.setEmail(user.getEmail());
        order.setUser(user);

        orderRepository.save(order);

        orderItemService.createOrderItems(cartItemService.getAllCartItemsByCartId(cartService.getCartByUser(user).getId()), order);

        return true;
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
    public List<Order> getAllOrdersReverseByUser(User user) {
        if (user == null || user.getId() == null) {
            throw new UserAuthenticationException();
        }

        List<Order> orderList = getAllOrdersByUserId(user.getId());
        Collections.reverse(orderList);

        return orderList;
    }

    @Override
    public boolean isOrderExistsById(Long id) {
        return orderRepository.existsById(id);
    }

    @Override
    public boolean changeDeliveryStatusByOrderId(Long id, String deliveryStatus) {
        Order order = getOrderById(id);
        order.setDeliveryStatus(deliveryStatus);

        orderRepository.save(order);
        return true;
    }

}
