package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;

public interface CartService {
    void createCartForUser(User user);

    Cart getCartByUser(User user);

    void addProductToCart(User user, Product product);
}
