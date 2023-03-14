package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);
}