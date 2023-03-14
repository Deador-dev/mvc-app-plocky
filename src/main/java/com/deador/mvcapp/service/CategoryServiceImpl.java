package com.deador.mvcapp.service;

import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.repository.CategoryRepository;
import com.deador.mvcapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public boolean deleteCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent() && productRepository.findAllByCategory(category).isEmpty()) {
            categoryRepository.delete(category);
            return true;
        } else {
            return false;
        }
    }
}
