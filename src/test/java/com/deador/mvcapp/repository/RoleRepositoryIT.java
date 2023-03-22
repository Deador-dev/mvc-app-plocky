package com.deador.mvcapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RoleRepositoryIT {
    private static final String EXISTING_NAME = "ROLE_ADMIN";
    private static final String NOT_EXISTING_NAME = "ROLE_SOME";

    private static final Integer RETURN_LIST_SIZE = 2;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findByExistingNameShouldReturnCorrectRoleEntity() {
        assertThat(roleRepository.findByName(EXISTING_NAME).get().getName()).isEqualTo(EXISTING_NAME);
    }

    @Test
    public void findByNotExistingNameShouldReturnOptionalEmpty() {
        assertThat(roleRepository.findByName(NOT_EXISTING_NAME)).isEqualTo(Optional.empty());
    }

    @Test
    public void findAllShouldReturnList() {
        assertThat(roleRepository.findAll()).hasSize(RETURN_LIST_SIZE);
        assertThat(roleRepository.findAll().get(0).getId()).isEqualTo(1L);
        assertThat(roleRepository.findAll().get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void existsByNameShouldReturnTrue() {
        assertTrue(roleRepository.existsByName(EXISTING_NAME));
    }

    @Test
    public void notExistsByNameShouldReturnFalse() {
        assertFalse(roleRepository.existsByName(NOT_EXISTING_NAME));
    }
}
