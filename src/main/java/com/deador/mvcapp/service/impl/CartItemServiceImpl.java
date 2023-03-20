package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.CartItem;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.repository.CartItemRepository;
import com.deador.mvcapp.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private static final String CART_ITEM_NOT_FOUND_BY_ID = "Cart item not found by id: %s";
    private static final String CART_ITEM_DELETING_ERROR = "Can't delete cart item by id: %s";
    private final ObjectFactory objectFactory;
    private final CartItemRepository cartItemRepository;


    @Autowired
    public CartItemServiceImpl(
            ObjectFactory objectFactory,
            CartItemRepository cartItemRepository) {
        this.objectFactory = objectFactory;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public boolean createCartItemByProduct(Cart cart, Product product) {
        CartItem cartItem = (CartItem) objectFactory.createObject(CartItem.class);

        cartItem.setCart(cart);
        cartItem.setProduct(product);

        cartItemRepository.save(cartItem);

        return true;
    }

    @Override
    public boolean deleteCartItemByCartItemId(Long id) {
        if (isCartItemExistsById(id)) {
            cartItemRepository.deleteById(id);
            return true;
        } else if (!isCartItemExistsById(id)) {
            throw new NotExistException(String.format(CART_ITEM_NOT_FOUND_BY_ID, id));
        } else {
            throw new NotExistException(String.format(CART_ITEM_DELETING_ERROR, id));
        }
    }

    @Override
    public List<CartItem> getAllCartItemsByCartId(Long id) {
        return cartItemRepository.findAllByCartId(id);
    }

    @Override
    public CartItem getCartItemById(Long id) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(id);
        if (optionalCartItem.isEmpty()) {
            throw new NotExistException(String.format(CART_ITEM_NOT_FOUND_BY_ID, id));
        }

        return optionalCartItem.get();
    }

    @Override
    public boolean isCartItemExistsById(Long id) {
        return cartItemRepository.existsById(id);
    }
}
