package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.CartItem;
import com.deador.mvcapp.entity.Order;
import com.deador.mvcapp.entity.OrderItem;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.repository.OrderItemRepository;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final ObjectFactory objectFactory;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;

    @Autowired
    public OrderItemServiceImpl(ObjectFactory objectFactory,
                                OrderItemRepository orderItemRepository,
                                CartService cartService) {
        this.objectFactory = objectFactory;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public boolean createOrderItems(List<CartItem> allCartItems, Order order) {
        for (CartItem cartItem : allCartItems) {
            OrderItem orderItem = (OrderItem) objectFactory.createObject(OrderItem.class);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setOrder(order);

            orderItemRepository.save(orderItem);
            cartService.deleteProductFromCartByCartItemId(order.getUser(), cartItem.getId());
        }

        return true;
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getAllOrderItemsByOrderId(Long id) {
        return orderItemRepository.findAllByOrderId(id);
    }

    @Override
    public List<OrderItem> getAllOrderItemsReverseByUser(User user) {
        if(user == null || user.getId() == null){
            throw new UserAuthenticationException();
        }

        List<OrderItem> orderItemListByUser = orderItemRepository.findByOrderUser(user);
        Collections.reverse(orderItemListByUser);

        return orderItemListByUser;
    }

}
