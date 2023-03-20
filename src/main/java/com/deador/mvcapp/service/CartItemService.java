package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.CartItem;
import com.deador.mvcapp.entity.Product;

import java.util.List;

public interface CartItemService {
    boolean createCartItemByProduct(Cart cart, Product product);

    boolean deleteCartItemByCartItemId(Long id);

    List<CartItem> getAllCartItemsByCartId(Long id);

    CartItem getCartItemById(Long id);

    boolean isCartItemExistsById(Long id);
}
