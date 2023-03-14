package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean createCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isEmpty()) {
            categoryRepository.save(category);
            return true;
        }

        return false;
    }

    @Override
    public List<Category> getAllCategories() {
        // FIXME: 14.03.2023 need to fix NullPointerException
        return categoryRepository.findAll();
    }

    @Override
    public boolean deleteCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            categoryRepository.delete(category);
            return true;
        } else {
            return false;
        }
    }
}
