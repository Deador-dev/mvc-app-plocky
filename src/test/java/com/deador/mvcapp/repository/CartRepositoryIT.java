package com.deador.mvcapp.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.deador.mvcapp.entity.Cart;
import com.deador.mvcapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class CartRepositoryIT {
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = 99L;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByExistingUserShouldReturnOptionalOfCart() {
        assertThat(userRepository.findById(EXISTING_USER_ID)).isInstanceOf(Optional.class);
        assertThat(userRepository.findById(EXISTING_USER_ID).get()).isInstanceOf(User.class);
        assertThat(userRepository.findById(EXISTING_USER_ID).get().getId()).isEqualTo(EXISTING_USER_ID);

        User user = userRepository.findById(EXISTING_USER_ID).get();

        assertThat(cartRepository.findByUser(user)).isInstanceOf(Optional.class);
        assertThat(cartRepository.findByUser(user).get()).isInstanceOf(Cart.class);
        assertThat(cartRepository.findByUser(user).get().getUser().getId()).isEqualTo(EXISTING_USER_ID);
    }

    @Test
    public void findByNotExistingUserShouldReturnOptionalEmpty() {
        User user = userRepository.findById(NOT_EXISTING_USER_ID).orElse(null);

        assertThat(user).isNull();

        assertThat(cartRepository.findByUser(user)).isEmpty();
    }
}
