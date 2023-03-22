package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.OrderItem;
import com.deador.mvcapp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderItemRepositoryIT {
    private static final Long EXISTING_ORDER_ID = 1L;
    private static final Long WRONG_ORDER_ID = 99L;
    private static final Long EXISTING_ORDER_USER_ID = 1L;
    private static final Long WRONG_ORDER_USER_ID = 99L;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAllShouldReturnListOfOrderItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAll();

        assertThat(orderItemList.size()).isGreaterThan(0);
        assertThat(orderItemList.get(0).getId()).isEqualTo(1L);
        assertThat(orderItemList.get(1).getId()).isEqualTo(2L);
        assertThat(orderItemList.get(2).getId()).isEqualTo(3L);
    }

    @Test
    public void findAllByExistingOrderUserShouldReturnListOfOrderItems() {
        assertThat(userRepository.findById(EXISTING_ORDER_USER_ID)).isInstanceOf(Optional.class);
        assertThat(userRepository.findById(EXISTING_ORDER_USER_ID).get()).isInstanceOf(User.class);
        assertThat(userRepository.findById(EXISTING_ORDER_USER_ID).get().getId()).isEqualTo(EXISTING_ORDER_USER_ID);

        User user = userRepository.findById(EXISTING_ORDER_USER_ID).get();

        for (OrderItem orderItem : orderItemRepository.findAllByOrderUser(user)) {
            assertThat(orderItem.getOrder().getUser().getId()).isEqualTo(EXISTING_ORDER_USER_ID);
        }
    }

    @Test
    public void findAllByWrongOrderUserShouldReturnEmptyList() {
        User user = userRepository.findById(WRONG_ORDER_USER_ID).orElse(null);

        assertThat(user).isNull();

        assertThat(orderItemRepository.findAllByOrderUser(user).size()).isEqualTo(0);
    }

    @Test
    public void findAllByExistingOrderIdShouldReturnListOfOrderItems() {
        List<OrderItem> orderItemList = orderItemRepository.findAllByOrderId(EXISTING_ORDER_ID);

        assertThat(orderItemList.size()).isGreaterThan(0);

        for (OrderItem orderItem : orderItemList) {
            assertThat(orderItem.getOrder().getId()).isEqualTo(EXISTING_ORDER_ID);
        }
    }

    @Test
    public void findAllByWrongOrderIdShouldReturnEmptyList() {
        assertThat(orderItemRepository.findAllByOrderId(WRONG_ORDER_ID).size()).isEqualTo(0);
    }
}
