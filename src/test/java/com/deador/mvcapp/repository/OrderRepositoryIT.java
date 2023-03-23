package com.deador.mvcapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deador.mvcapp.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class OrderRepositoryIT {
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 99L;
    private static final Long EXISTING_USER_ID = 1L;
    private static final Long NOT_EXISTING_USER_ID = 99L;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findAllShouldReturnListOfOrders() {
        List<Order> orderList = orderRepository.findAll();

        assertThat(orderList.size()).isGreaterThan(0);
        assertThat(orderList.get(0).getId()).isEqualTo(1L);
        assertThat(orderList.get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void findByExistingIdShouldReturnOptionalOfOrder() {
        assertThat(orderRepository.findById(EXISTING_ID)).isInstanceOf(Optional.class);
        assertThat(orderRepository.findById(EXISTING_ID).get()).isInstanceOf(Order.class);
        assertThat(orderRepository.findById(EXISTING_ID).get().getId()).isEqualTo(EXISTING_ID);
    }

    @Test
    public void findByNotExistingIdShouldReturnOptionalEmpty() {
        assertThat(orderRepository.findById(NOT_EXISTING_ID)).isEmpty();
    }

    @Test
    public void findAllByExistingUserIdShouldReturnListOfOrders() {
        List<Order> orderList = orderRepository.findAllByUserId(EXISTING_USER_ID);

        // FIXME: 22.03.2023 Any user can have 0 orders.
//        assertThat(orderList.size()).isGreaterThan(0);

        for (Order order : orderList) {
            assertThat(order.getUser().getId()).isEqualTo(EXISTING_ID);
        }
    }

    @Test
    public void findAllByNotExistingUserIdShouldReturnEmptyList() {
        assertThat(orderRepository.findAllByUserId(NOT_EXISTING_USER_ID).size()).isEqualTo(0);
    }

    @Test
    public void existsByExistingIdShouldReturnTrue() {
        assertTrue(orderRepository.existsById(EXISTING_ID));
    }

    @Test
    public void existsByNotExistingIdShouldReturnFalse() {
        assertFalse(orderRepository.existsById(NOT_EXISTING_ID));
    }
}
