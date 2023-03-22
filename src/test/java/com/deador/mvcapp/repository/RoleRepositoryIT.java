package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RoleRepositoryIT {
    private static final String EXISTING_NAME = "ROLE_ADMIN";
    private static final String WRONG_NAME = "ROLE_SOME";
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void findAllShouldReturnListOfRoles() {
        List<Role> roleList = roleRepository.findAll();

        assertThat(roleList.size()).isGreaterThan(0);
        assertThat(roleList.get(0).getId()).isEqualTo(1L);
        assertThat(roleList.get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void findByExistingNameShouldReturnOptionalOfRole() {
        assertThat(roleRepository.findByName(EXISTING_NAME)).isInstanceOf(Optional.class);
        assertThat(roleRepository.findByName(EXISTING_NAME).get()).isInstanceOf(Role.class);
        assertThat(roleRepository.findByName(EXISTING_NAME).get().getName()).isEqualTo(EXISTING_NAME);
    }

    @Test
    public void findByWrongNameShouldReturnOptionalEmpty() {
        assertThat(roleRepository.findByName(WRONG_NAME)).isEmpty();
    }

    @Test
    public void existsByExistingNameShouldReturnTrue() {
        assertTrue(roleRepository.existsByName(EXISTING_NAME));
    }

    @Test
    public void existsByWrongNameShouldReturnFalse() {
        assertFalse(roleRepository.existsByName(WRONG_NAME));
    }
}
