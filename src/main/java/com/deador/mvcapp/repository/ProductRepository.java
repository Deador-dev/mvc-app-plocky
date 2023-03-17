package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);

    Optional<Category> findByName(String name);

    Page<Product> findAllByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findAllByNameContaining(String keyword, Pageable pageable);
}