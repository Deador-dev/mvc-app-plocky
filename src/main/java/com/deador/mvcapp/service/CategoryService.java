package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    boolean createOrUpdateCategory(Category category);

    boolean deleteCategoryById(Long id);

    boolean isCategoryExistsById(Long id);
}
