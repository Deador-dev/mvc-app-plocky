package com.deador.mvcapp.service.impl;

import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.exception.NotExistException;
import com.deador.mvcapp.repository.CategoryRepository;
import com.deador.mvcapp.repository.ProductRepository;
import com.deador.mvcapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String CATEGORY_CREATING_ERROR = "Category isn't created or updated";
    private static final String CATEGORY_DELETING_ERROR = "Can't delete category cause of relationship by id: %s";
    private static final String CATEGORY_UPDATING_ERROR = "Can't update category by id: %s";
    private static final String CATEGORY_ALREADY_EXIST = "Category already exist with name %s";
    private static final String CATEGORY_NOT_FOUND_BY_ID = "Category not found by id: %s";


    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new NotExistException(String.format(CATEGORY_NOT_FOUND_BY_ID, id));
        }

        return optionalCategory.get();
    }

    @Override
    @Transactional
    public boolean createOrUpdateCategory(Category category) {
        // Update category
        if (category.getId() != null && isCategoryExistsById(category.getId())) {
            Category existingCategory = categoryRepository.findById(category.getId()).orElse(null);
            if (existingCategory != null && !existingCategory.getName().equals(category.getName())) {
                // Check if new category name already exists
                if (categoryRepository.findByName(category.getName()).isPresent()) {
                    throw new NotExistException(String.format(CATEGORY_UPDATING_ERROR, category.getId()));
                }
                categoryRepository.save(category);
                return true;
            }
        }
        // Create new category
        else if (category.getId() == null && category.getName() != null && categoryRepository.findByName(category.getName()).isEmpty()) {
            categoryRepository.save(category);
            return true;
        }
        // Throw exception if category is not updated or created
        throw new NotExistException(CATEGORY_CREATING_ERROR);
    }


    @Override
    @Transactional
    public boolean deleteCategoryById(Long id) {
        if (isCategoryExistsById(id) && productRepository.findAllByCategory(getCategoryById(id)).isEmpty()) {
            categoryRepository.deleteById(id);
            return true;
        } else if (!isCategoryExistsById(id)) {
            throw new NotExistException(String.format(CATEGORY_NOT_FOUND_BY_ID, id));
        } else {
            throw new NotExistException(String.format(CATEGORY_DELETING_ERROR, id));
        }
    }

    public boolean isCategoryExistsById(Long id) {
        return categoryRepository.existsById(id);
    }
}