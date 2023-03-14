package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    boolean createCategory(Category category);

    boolean deleteCategory(Category category);

    List<Category> getAllCategories();

    Optional<Category> getCategoryByName(String name);
}
