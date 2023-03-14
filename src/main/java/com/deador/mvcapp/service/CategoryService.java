package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Category;

import java.util.List;

public interface CategoryService {
    boolean createCategory(Category category);
    List<Category> getAllCategories();

    boolean deleteCategory(Category category);
}
