package com.deador.mvcapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deador.mvcapp.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CategoryRepositoryIT {
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 99L;
    private static final String EXISTING_NAME = "Samsung";
    private static final String NOT_EXISTING_NAME = "WrongCategoryName";

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void findAllShouldReturnListOfCategories() {
        List<Category> categoryList = categoryRepository.findAll();

        assertThat(categoryList.size()).isGreaterThan(0);
        assertThat(categoryList.get(0).getId()).isEqualTo(1L);
        assertThat(categoryList.get(1).getId()).isEqualTo(2L);
        assertThat(categoryList.get(2).getId()).isEqualTo(3L);
    }

    @Test
    public void findByExistingIdShouldReturnOptionalOfCategory() {
        assertThat(categoryRepository.findById(EXISTING_ID)).isInstanceOf(Optional.class);
        assertThat(categoryRepository.findById(EXISTING_ID).get()).isInstanceOf(Category.class);
        assertThat(categoryRepository.findById(EXISTING_ID).get().getId()).isEqualTo(EXISTING_ID);
    }

    @Test
    public void findByNotExistingIdShouldReturnOptionalEmpty() {
        assertThat(categoryRepository.findById(NOT_EXISTING_ID)).isEmpty();
    }

    @Test
    public void findByExistingNameShouldReturnOptionalOfCategory() {
        assertThat(categoryRepository.findByName(EXISTING_NAME)).isInstanceOf(Optional.class);
        assertThat(categoryRepository.findByName(EXISTING_NAME).get()).isInstanceOf(Category.class);
        assertThat(categoryRepository.findByName(EXISTING_NAME).get().getName()).isEqualTo(EXISTING_NAME);
    }

    @Test
    public void findByNotExistingNameShouldReturnOptionalEmpty() {
        assertThat(categoryRepository.findByName(NOT_EXISTING_NAME)).isEmpty();
    }

    @Test
    public void existsByExistingIdShouldReturnTrue() {
        assertTrue(categoryRepository.existsById(EXISTING_ID));
    }

    @Test
    public void existsByNotExistingIdShouldReturnFalse() {
        assertFalse(categoryRepository.existsById(NOT_EXISTING_ID));
    }
}
