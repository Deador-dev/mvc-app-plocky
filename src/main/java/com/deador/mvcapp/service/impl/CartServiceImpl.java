package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.repository.CartRepository;
import com.deador.mvcapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private static final String CART_NOT_FOUND_BY_USER = "Cart not found by user";
    private final CartRepository cartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public boolean createCartForUser(User user) {
        Cart cart = new Cart();
        cart.setPrice(0.0);
        cart.setQuantity(0);
        cart.setUser(user);

        cartRepository.save(cart);
        return true;
    }

    @Override
    public Integer getCartQuantityByUser(User user) {
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        if (optionalCart.isEmpty()) {
            return 0;
        }

        return cartRepository.findByUser(user).get().getQuantity();
    }

    @Override
    public boolean addProductToCart(User user, Product product) {
        // TODO: 14.03.2023 need to impl
        return true;
    }
}
