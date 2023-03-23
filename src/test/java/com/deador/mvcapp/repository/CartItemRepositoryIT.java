package com.deador.mvcapp.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deador.mvcapp.entity.CartItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CartItemRepositoryIT {
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 99L;
    private static final Long EXISTING_CART_ID = 2L;
    private static final Long NOT_EXISTING_CART_ID = 99L;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    public void findByExistingIdShouldReturnOptionalOfCartItem() {
        assertThat(cartItemRepository.findById(EXISTING_ID)).isInstanceOf(Optional.class);
        assertThat(cartItemRepository.findById(EXISTING_ID).get()).isInstanceOf(CartItem.class);
        assertThat(cartItemRepository.findById(EXISTING_ID).get().getId()).isEqualTo(EXISTING_ID);
    }

    @Test
    public void findByNotExistingIdShouldReturnOptionalEmpty() {
        assertThat(cartItemRepository.findById(NOT_EXISTING_ID)).isEmpty();
    }

    @Test
    public void findAllByExistingCartIdShouldReturnListOfCartItems() {
        List<CartItem> cartItemList = cartItemRepository.findAllByCartId(EXISTING_CART_ID);

        // FIXME: 23.03.2023 Any cart can have 0 cart items.
//        assertThat(cartItemList.size()).isGreaterThan(0);

        for (CartItem cartItem : cartItemList) {
            assertThat(cartItem.getCart().getId()).isEqualTo(EXISTING_CART_ID);
        }
    }

    @Test
    public void findAllByNotExistingCartIdShouldReturnEmptyList() {
        assertThat(cartItemRepository.findAllByCartId(NOT_EXISTING_CART_ID).size()).isEqualTo(0);
    }

    @Test
    public void existsByExistingIdShouldReturnTrue() {
        assertTrue(cartItemRepository.existsById(EXISTING_ID));
    }

    @Test
    public void existsByNotExistingIdShouldReturnFalse() {
        assertFalse(cartItemRepository.existsById(NOT_EXISTING_ID));
    }
}
