package com.deador.mvcapp.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.deador.mvcapp.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class ProductRepositoryIT {
    private static final Long EXISTING_ID = 1L;
    private static final Long NOT_EXISTING_ID = 99L;
    private static final Long EXISTING_CATEGORY_ID = 1L;
    private static final Long NOT_EXISTING_CATEGORY_ID = 99L;
    private static final String EXISTING_NAME_CONTAINING = "Samsung";
    private static final String NOT_EXISTING_NAME_CONTAINING = "WrongText";

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAllShouldReturnListOfProducts() {
        List<Product> productList = productRepository.findAll();

        assertThat(productList.size()).isGreaterThan(0);
        assertThat(productList.get(0).getId()).isEqualTo(1L);
        assertThat(productList.get(1).getId()).isEqualTo(2L);
    }

    @Test
    public void findByExistingIdShouldReturnOptionalOfProduct() {
        assertThat(productRepository.findById(EXISTING_ID)).isInstanceOf(Optional.class);
        assertThat(productRepository.findById(EXISTING_ID).get()).isInstanceOf(Product.class);
        assertThat(productRepository.findById(EXISTING_ID).get().getId()).isEqualTo(EXISTING_ID);
    }

    @Test
    public void findByNotExistingIdShouldReturnOptionalEmpty() {
        assertThat(productRepository.findById(NOT_EXISTING_ID)).isEmpty();
    }

    @Test
    public void findAllByExistingCategoryIdShouldReturnListOfProducts() {
        List<Product> productList = productRepository.findAllByCategoryId(EXISTING_CATEGORY_ID);

        assertThat(productList.size()).isGreaterThan(0);

        for (Product product : productList) {
            assertThat(product.getCategory().getId()).isEqualTo(EXISTING_CATEGORY_ID);
        }
    }

    @Test
    public void findAllByNotExistingCategoryIdShouldReturnEmptyList() {
        assertThat(productRepository.findAllByCategoryId(NOT_EXISTING_CATEGORY_ID).size()).isEqualTo(0);
    }

    @Test
    public void findAllByExistingCategoryIdShouldReturnPageOfProducts() {
        Pageable pageable = PageRequest.of(0, 6);

        Page<Product> page = productRepository.findAllByCategoryId(EXISTING_CATEGORY_ID, pageable);

        assertThat(page.getContent().size()).isGreaterThan(0).isLessThanOrEqualTo(6);

        for (Product product : page.getContent()) {
            assertThat(product.getCategory().getId()).isEqualTo(EXISTING_CATEGORY_ID);
        }
    }

    @Test
    public void findAllByNotExistingCategoryIdShouldReturnEmptyPage() {
        Pageable pageable = PageRequest.of(0, 6);

        assertThat(productRepository.findAllByCategoryId(NOT_EXISTING_CATEGORY_ID, pageable).getContent().size()).isEqualTo(0);
    }

    @Test
    public void findAllByExistingNameContainingShouldReturnPageOfProducts() {
        Pageable pageable = PageRequest.of(0, 6);

        Page<Product> page = productRepository.findAllByNameContaining(EXISTING_NAME_CONTAINING, pageable);

        assertThat(page.getContent().size()).isGreaterThan(0).isLessThanOrEqualTo(6);

        for (Product product : page.getContent()) {
            assertThat(product.getName()).contains(EXISTING_NAME_CONTAINING);
        }
    }

    @Test
    public void findAllByNotExistingNameContainingShouldReturnEmptyPage() {
        Pageable pageable = PageRequest.of(0, 6);

        assertThat(productRepository.findAllByNameContaining(NOT_EXISTING_NAME_CONTAINING, pageable).getContent().size()).isEqualTo(0);
    }

    @Test
    public void existsByExistingIdShouldReturnTrue() {
        assertTrue(productRepository.existsById(EXISTING_ID));
    }

    @Test
    public void existsByNotExistingIdShouldReturnFalse() {
        assertFalse(productRepository.existsById(NOT_EXISTING_ID));
    }
}
