package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    boolean createOrUpdateCategory(Category category);

    boolean deleteCategory(Category category);

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    boolean categoryExists(Category category);
}
