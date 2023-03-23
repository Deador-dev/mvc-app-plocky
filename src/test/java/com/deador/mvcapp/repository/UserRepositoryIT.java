package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class UserRepositoryIT {
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 99L;
    private static final String EXISTING_EMAIL = "admin@gmail.com";
    private static final String NOT_EXISTING_EMAIL = "wrong@gmail.com";
    private static final String EXISTING_ACTIVATION_CODE = "98022005-c59b-4dee-bb9f-d092873216f0";
    private static final String NOT_EXISTING_ACTIVATION_CODE = "Wrong-activation-code";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByExistingIdShouldReturnOptionalOfUser() {
        assertThat(userRepository.findById(EXISTING_ID)).isInstanceOf(Optional.class);
        assertThat(userRepository.findById(EXISTING_ID).get()).isInstanceOf(User.class);
        assertThat(userRepository.findById(EXISTING_ID).get().getId()).isEqualTo(EXISTING_ID);
    }

    @Test
    public void findByNotExistingIdShouldReturnOptionalEmpty() {
        assertThat(userRepository.findById(NOT_EXISTING_ID)).isEmpty();
    }

    @Test
    public void findByExistingEmailShouldReturnOptionalOfUser() {
        assertThat(userRepository.findByEmail(EXISTING_EMAIL)).isInstanceOf(Optional.class);
        assertThat(userRepository.findByEmail(EXISTING_EMAIL).get()).isInstanceOf(User.class);
        assertThat(userRepository.findByEmail(EXISTING_EMAIL).get().getEmail()).isEqualTo(EXISTING_EMAIL);
    }

    @Test
    public void findByNotExistingEmailShouldReturnOptionalEmpty() {
        assertThat(userRepository.findByEmail(NOT_EXISTING_EMAIL)).isEmpty();
    }

    @Test
    public void findByExistingActivationCodeShouldReturnOptionalOfUser() {
        assertThat(userRepository.findByActivationCode(EXISTING_ACTIVATION_CODE)).isInstanceOf(Optional.class);
        assertThat(userRepository.findByActivationCode(EXISTING_ACTIVATION_CODE).get()).isInstanceOf(User.class);
        assertThat(userRepository.findByActivationCode(EXISTING_ACTIVATION_CODE).get().getActivationCode()).isEqualTo(EXISTING_ACTIVATION_CODE);
    }

    @Test
    public void findByNotExistingActivationCodeShouldReturnOptionalEmpty() {
        assertThat(userRepository.findByActivationCode(NOT_EXISTING_ACTIVATION_CODE)).isEmpty();
    }

    @Test
    public void existsByExistingEmailShouldReturnTrue() {
        assertTrue(userRepository.existsByEmail(EXISTING_EMAIL));
    }

    @Test
    public void existsByNotExistingEmailShouldReturnFalse() {
        assertFalse(userRepository.existsByEmail(NOT_EXISTING_EMAIL));
    }
}
