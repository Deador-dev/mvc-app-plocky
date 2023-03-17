package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    boolean createOrUpdateCategory(Category category);

    boolean deleteCategoryById(Long id);

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    boolean isCategoryExistsById(Long id);
}
