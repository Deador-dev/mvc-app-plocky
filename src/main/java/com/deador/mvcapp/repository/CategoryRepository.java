package com.deador.mvcapp.repository;

import com.deador.mvcapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    boolean existsById(Long id);
}