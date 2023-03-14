package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
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
    public Cart getCartByUser(User user) {
        // FIXME: 14.03.2023 need to create custom exception or something
        return cartRepository.findByUser(user).get();
    }

    @Override
    public boolean addProductToCart(User user, Product product) {
        // TODO: 14.03.2023 need to impl
        return true;
    }
}
