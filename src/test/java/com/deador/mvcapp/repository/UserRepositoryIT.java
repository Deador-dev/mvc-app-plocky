package com.deador.mvcapp.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryIT {
    @Autowired
    private UserRepository userRepository;

    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String WRONG_EMAIL = "wrong@gmail.com";
    private static final String USER_EMAIL = "someemail@gmail.com";

    @Test
    public void existsByAdminEmailShouldReturnTrue() {
        assertTrue(userRepository.existsByEmail(ADMIN_EMAIL));
    }
}
