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
    public boolean createOrUpdateCategory(Category category) {
        if (category != null && categoryRepository.findByName(category.getName()).isEmpty()) {
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
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public boolean categoryExists(Category category) {
        return category != null && categoryRepository.findById(category.getId()).isPresent();
    }

    @Override
    public boolean deleteCategory(Category category) {
        if (category != null && categoryRepository.findById(category.getId()).isPresent() && productRepository.findAllByCategory(category).isEmpty()) {
            categoryRepository.delete(category);
            return true;
        } else {
            return false;
        }
    }
}
