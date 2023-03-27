package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.CartItem;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.exception.UserAuthenticationException;
import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.repository.CartRepository;
import com.deador.mvcapp.repository.UserRepository;
import com.deador.mvcapp.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 99L;
    private static final Double CURRENT_CART_PRICE = 100.0;
    private static final Double EMPTY_CART_PRICE = 0.0;
    private static final Integer CURRENT_CART_QUANTITY = 1;
    private static final Integer EMPTY_CART_QUANTITY = 0;
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = 99L;
    private static final String EXISTING_USER_EMAIL = "admin@gmail.com";
    private static final String NOT_EXISTING_USER_EMAIL = "not_existing@gmail.com";
    private static final Long EXISTING_PRODUCT_ID = 1L;
    private static final Long NOT_EXISTING_PRODUCT_ID = 99L;
    private static final Double PRODUCT_PRICE = 100.0;
    private static final Long EXISTING_CART_ITEM_ID = 1L;
    private static final Long NOT_EXISTING_CART_ITEM_ID = 99L;
    private static final String USER_NOT_FOUND_BY_ID = "User not found by id: %s";
    private static final String USER_NOT_FOUND_BY_EMAIL = "User not found by email: %s";
    private static final String USER_NOT_FOUND_FOR_CREATING_CART = "User not found for creating cart";
    private static final String CART_NOT_FOUND_BY_USER = "Cart not found by user";

    @Mock
    private ObjectFactory objectFactory;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private CartServiceImpl cartService;
    private User user;
    private Cart cart;
    private Product product;
    private CartItem cartItem;

    @BeforeEach
    void init() {
        user = User.builder().id(EXISTING_USER_ID).email(EXISTING_USER_EMAIL).build();
        cart = Cart.builder().id(EXISTING_ID).price(CURRENT_CART_PRICE).quantity(CURRENT_CART_QUANTITY).user(user).build();
        product = Product.builder().id(EXISTING_PRODUCT_ID).price(PRODUCT_PRICE).build();
        cartItem = CartItem.builder().id(EXISTING_CART_ITEM_ID).cart(cart).product(product).build();
    }

    @Test
    @DisplayName("Should return cart for existing user")
    void givenUser_whenGetCartByUser_thenReturnCart() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        assertThat(cartService.getCartByUser(user)).isNotNull().isEqualTo(cart);
    }

    @Test
    @DisplayName("Should throw UserAuthenticationException for null user")
    void givenNullUser_whenGetCartByUser_thenThrowUserAuthenticationException() {
        assertThatThrownBy(() -> cartService.getCartByUser(null))
                .isInstanceOf(UserAuthenticationException.class);

        assertThatThrownBy(() -> cartService.getCartByUser(User.builder().id(null).build()))
                .isInstanceOf(UserAuthenticationException.class);
    }

    @Test
    @DisplayName("Should throw NotExistException for not existing user")
    void givenNotExistingUser_whenGetCartByUser_thenThrowNotExistException() {
        User notExistingUser = User.builder().id(NOT_EXISTING_USER_ID).build();

        when(cartRepository.findByUser(notExistingUser)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.getCartByUser(notExistingUser))
                .isInstanceOf(NotExistException.class)
                .hasMessage(CART_NOT_FOUND_BY_USER);
    }

    @Test
    @DisplayName("Should return cart price for existing user")
    void givenUser_whenGetCartPriceByUser_thenReturnCartPrice() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        assertThat(cartService.getCartPriceByUser(user)).isNotNull().isEqualTo(CURRENT_CART_PRICE);
    }

    @Test
    @DisplayName("Should throw UserAuthenticationException for null user")
    void givenNullUser_whenGetCartPriceByUser_thenThrowUserAuthenticationException() {
        assertThatThrownBy(() -> cartService.getCartPriceByUser(null))
                .isInstanceOf(UserAuthenticationException.class);

        assertThatThrownBy(() -> cartService.getCartPriceByUser(User.builder().id(null).build()))
                .isInstanceOf(UserAuthenticationException.class);
    }

    @Test
    @DisplayName("Should return empty cart price for not existing user")
    void givenNotExistingUser_whenGetCartPriceByUser_thenReturnEmptyCartPrice() {
        User notExistingUser = User.builder().id(NOT_EXISTING_USER_ID).build();

        when(cartRepository.findByUser(notExistingUser)).thenReturn(Optional.empty());

        assertEquals(cartService.getCartPriceByUser(notExistingUser), EMPTY_CART_PRICE);
    }

    @Test
    @DisplayName("Should return cart quantity for existing user")
    void givenUser_whenGetCartQuantityByUser_thenReturnCartQuantity() {
        when(userRepository.findByEmail(EXISTING_USER_EMAIL)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        assertThat(cartService.getCartQuantityByUser(user)).isNotNull().isEqualTo(CURRENT_CART_QUANTITY);
    }

    @Test
    @DisplayName("Should throw UserAuthenticationException for existing not authenticated user")
    void givenNotAuthenticatedUser_whenGetCartQuantityByUser_thenThrowUserAuthenticationException() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        assertThatThrownBy(() -> cartService.getCartQuantityByUser(user))
                .isInstanceOf(UserAuthenticationException.class);
    }

    @Test
    @DisplayName("Should return empty cart quantity for null user")
    void givenNullUser_whenGetCartQuantityByUser_thenReturnEmptyCartQuantity() {
        assertThat(cartService.getCartQuantityByUser(null)).isNotNull().isEqualTo(EMPTY_CART_QUANTITY);
        assertThat(cartService.getCartQuantityByUser(User.builder().id(null).build())).isNotNull().isEqualTo(EMPTY_CART_QUANTITY);
    }

    @Test
    @DisplayName("Should throw NotExistException for not existing user")
    void givenNotExistingUserEmail_whenGetCartQuantityByUser_thenThrowNotExistsException() {
        User notExistingUser = User.builder().id(NOT_EXISTING_USER_ID).email(NOT_EXISTING_USER_EMAIL).build();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        when(userRepository.findByEmail(NOT_EXISTING_USER_EMAIL)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> cartService.getCartQuantityByUser(notExistingUser))
                .isInstanceOf(NotExistException.class)
                .hasMessage(String.format(USER_NOT_FOUND_BY_EMAIL, NOT_EXISTING_USER_EMAIL));
    }

    @Test
    @DisplayName("Should return empty cart quantity for null cart")
    void givenUserWithNullCart_whenGetCartQuantityByUser_thenReturnEmptyCartQuantity() {
        when(userRepository.findByEmail(EXISTING_USER_EMAIL)).thenReturn(Optional.of(user));
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        assertThat(cartService.getCartQuantityByUser(user)).isNotNull().isEqualTo(EMPTY_CART_QUANTITY);
    }

    @Test
    @DisplayName("Should return boolean for existing user")
    void givenUser_whenCreateCartForUser_thenReturnTrue() {
        User actualUser = User.builder().id(NOT_EXISTING_ID).build();
        Cart actualCart = Cart.builder().build();

        when(objectFactory.createObject(Cart.class)).thenReturn(actualCart);

        assertTrue(cartService.createCartForUser(actualUser));
        verify(objectFactory, times(1)).createObject(Cart.class);
        verify(cartRepository, times(1)).save(actualCart);
    }

    @Test
    @DisplayName("Should throw NotExistException for null user")
    void givenNullUser_whenCreateCartForUser_thenThrowNotExistException() {
        assertThatThrownBy(() -> cartService.createCartForUser(null))
                .isInstanceOf(NotExistException.class)
                .hasMessage(USER_NOT_FOUND_FOR_CREATING_CART);

        assertThatThrownBy(() -> cartService.createCartForUser(User.builder().id(null).build()))
                .isInstanceOf(NotExistException.class)
                .hasMessage(USER_NOT_FOUND_FOR_CREATING_CART);
    }

}
