package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findAllByCategoryId(Long id);

    Page<Product> findAllByCategoryId(Long id, Pageable pageable);

    Page<Product> findAllByNameContaining(String keyword, Pageable pageable);

    boolean existsById(Long id);
}