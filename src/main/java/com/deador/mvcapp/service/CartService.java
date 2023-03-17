package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;

public interface CartService {
    boolean createCartForUser(User user);

    Integer getCartQuantityByUser(User user);

    boolean addProductToCart(User user, Product product);
}
