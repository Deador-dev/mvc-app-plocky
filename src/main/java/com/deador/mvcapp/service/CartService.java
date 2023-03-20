package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.User;

public interface CartService {
    boolean createCartForUser(User user);

    boolean addProductToCartByProductId(User user, Long id);

    boolean deleteProductFromCartByCartItemId(User user, Long id);

    boolean buyNowByProductId(User user, Long id);

    Cart getCartByUser(User user);

    Double getCartPriceByUser(User user);

    Integer getCartQuantityByUser(User user);
}
