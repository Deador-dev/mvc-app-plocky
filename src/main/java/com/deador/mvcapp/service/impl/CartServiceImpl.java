package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.CartItem;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.repository.CartRepository;
import com.deador.mvcapp.repository.UserRepository;
import com.deador.mvcapp.service.CartItemService;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private static final String USER_NOT_FOUND_BY_EMAIL = "User not found by email: %s";
    private static final String CART_NOT_FOUND_BY_USER = "Cart not found by user";
    private final ObjectFactory objectFactory;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartItemService cartItemService;


    @Autowired
    public CartServiceImpl(ObjectFactory objectFactory,
                           UserRepository userRepository,
                           CartRepository cartRepository,
                           ProductService productService,
                           CartItemService cartItemService) {
        this.objectFactory = objectFactory;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @Override
    @Transactional
    public boolean createCartForUser(User user) {
        Cart cart = (Cart) objectFactory.createObject(Cart.class);
        cart.setPrice(0.0);
        cart.setQuantity(0);
        cart.setUser(user);

        cartRepository.save(cart);
        return true;
    }

    @Override
    @Transactional
    public boolean addProductToCartByProductId(User user, Long id) {
        Cart cart = getCartByUser(user);
        Product product = productService.getProductById(id);

        cartItemService.createCartItemByProduct(cart, product);

        cart.setQuantity(cart.getQuantity() + 1);
        cart.setPrice(cart.getPrice() + product.getPrice());

        cartRepository.save(cart);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteProductFromCartByCartItemId(User user, Long id) {
        Cart cart = getCartByUser(user);
        CartItem cartItem = cartItemService.getCartItemById(id);

        cartItemService.deleteCartItemByCartItemId(id);

        cart.setQuantity(cart.getQuantity() - 1);
        cart.setPrice(cart.getPrice() - cartItem.getProduct().getPrice());

        return true;
    }

    @Override
    public boolean buyNowByProductId(User user, Long id) {
        Cart cart = getCartByUser(user);
        List<CartItem> cartItemList = cartItemService.getAllCartItemsByCartId(cart.getId());


        for (CartItem cartItem : cartItemList) {
            deleteProductFromCartByCartItemId(user, cartItem.getId());
        }

        addProductToCartByProductId(user, id);
        return true;
    }

    @Override
    public Cart getCartByUser(User user) {
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        if (optionalCart.isEmpty()) {
            throw new NotExistException(String.format(CART_NOT_FOUND_BY_USER));
        }

        return optionalCart.get();
    }

    @Override
    public Double getCartPriceByUser(User user) {
        Optional<Cart> optionalCart = cartRepository.findByUser(user);
        if (optionalCart.isEmpty()) {
            return 0.0;
        }

        return cartRepository.findByUser(user).get().getPrice();
    }

    @Override
    public Integer getCartQuantityByUser(User user) {
        if (user == null || user.getId() == null) {
            return 0;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UserAuthenticationException();
        }

        User userFromDB = userRepository.findUserByEmail(user.getEmail())
                .orElseThrow(() -> new NotExistException(String.format(USER_NOT_FOUND_BY_EMAIL, user.getEmail())));

        Optional<Cart> optionalCart = cartRepository.findByUser(userFromDB);
        if (optionalCart.isEmpty()) {
            return 0;
        }

        return cartRepository.findByUser(user).get().getQuantity();
    }
}
