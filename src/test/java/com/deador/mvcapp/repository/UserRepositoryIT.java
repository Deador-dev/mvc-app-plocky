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
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String USER_EMAIL = "user@gmail.com";
    private static final String WRONG_EMAIL = "wrong@gmail.com";
    private static final String EXISTING_ACTIVATION_CODE = "98022005-c59b-4dee-bb9f-d092873216f0";
    private static final String WRONG_ACTIVATION_CODE = "Wrong-activation-code";
    private static final Long EXISTING_ID = 1L;
    private static final Long WRONG_ID = 99L;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByExistingIdShouldReturnOptionalOfUser() {
        assertThat(userRepository.findById(EXISTING_ID)).isInstanceOf(Optional.class);
        assertThat(userRepository.findById(EXISTING_ID).get()).isInstanceOf(User.class);
        assertThat(userRepository.findById(EXISTING_ID).get().getId()).isEqualTo(EXISTING_ID);
    }

    @Test
    public void findByWrongIdShouldReturnOptionalEmpty() {
        assertThat(userRepository.findById(WRONG_ID)).isEmpty();
    }

    @Test
    public void findByAdminEmailShouldReturnOptionalOfUser() {
        assertThat(userRepository.findByEmail(ADMIN_EMAIL)).isInstanceOf(Optional.class);
        assertThat(userRepository.findByEmail(ADMIN_EMAIL).get()).isInstanceOf(User.class);
        assertThat(userRepository.findByEmail(ADMIN_EMAIL).get().getEmail()).isEqualTo(ADMIN_EMAIL);
    }

    @Test
    public void findByWrongEmailShouldReturnOptionalEmpty() {
        assertThat(userRepository.findByEmail(WRONG_EMAIL)).isEmpty();
    }

    @Test
    public void findByExistingActivationCodeShouldReturnOptionalOfUser() {
        assertThat(userRepository.findByActivationCode(EXISTING_ACTIVATION_CODE)).isInstanceOf(Optional.class);
        assertThat(userRepository.findByActivationCode(EXISTING_ACTIVATION_CODE).get()).isInstanceOf(User.class);
        assertThat(userRepository.findByActivationCode(EXISTING_ACTIVATION_CODE).get().getActivationCode()).isEqualTo(EXISTING_ACTIVATION_CODE);
    }

    @Test
    public void findByWrongActivationCodeShouldReturnOptionalEmpty() {
        assertThat(userRepository.findByActivationCode(WRONG_ACTIVATION_CODE)).isEmpty();
    }


    @Test
    public void existsByExistingEmailShouldReturnTrue() {
        assertTrue(userRepository.existsByEmail(USER_EMAIL));
    }

    @Test
    public void existsByWrongEmailShouldReturnFalse() {
        assertFalse(userRepository.existsByEmail(WRONG_EMAIL));
    }
}
