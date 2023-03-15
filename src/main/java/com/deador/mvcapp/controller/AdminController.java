package com.deador.mvcapp.controller;

import com.deador.mvcapp.converter.DTOConverter;
import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.dto.ProductDTO;
import com.deador.mvcapp.service.CategoryService;
import com.deador.mvcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final DTOConverter dtoConverter;

    @Autowired
    public AdminController(CategoryService categoryService, ProductService productService, DTOConverter dtoConverter) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping
    public String viewAdminHome() {
        return "/adminHome";
    }

    @GetMapping("/categories")
    public String viewCategories(Model model) {
        prepareCategoriesModel(model);
        return "/categories";
    }

    @GetMapping("/categories/add")
    public String getCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "/categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String createCategory(@ModelAttribute("category") Category category,
                                 Model model) {
        if (categoryService.createOrUpdateCategory(category)) {
            return "redirect:/admin/categories";
        } else {
            model.addAttribute("creatingCategoryError", "Creating category error");
            return "/categoriesAdd";
        }

    }

    @DeleteMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id", required = false) Category category,
                                 Model model) {
        if (categoryService.deleteCategory(category)) {
            return "redirect:/admin/categories";
        } else if (!categoryService.categoryExists(category)) {
            model.addAttribute("deletingNonExistentCategoryError", "Deleting non-existent category");
            // FIXME: 15.03.2023 forward?
            // You cannot use forward.
            // When using forward, the request continues to be a DELETE request,
            // which is not supported on the page you are redirecting to.
            prepareCategoriesModel(model);
            return "/categories";
        } else {
            model.addAttribute("deletingCategoryError", "Error deleting category");
            // FIXME: 15.03.2023 forward?
            // You cannot use forward.
            // When using forward, the request continues to be a DELETE request,
            // which is not supported on the page you are redirecting to.
            prepareCategoriesModel(model);
            return "/categories";
        }
    }

    // FIXME: 15.03.2023 need to create update form (Currently, 1 form is used to create and update a category)
    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable(name = "id", required = false) Category category,
                                 Model model) {
        if (categoryService.categoryExists(category)) {
            model.addAttribute("category", category);
            return "/categoriesAdd";
        } else {
            model.addAttribute("updatingCategoryError", "Error updating category");
            // FIXME: 15.03.2023 forward?
            // You cannot use forward.
            // When using forward, the request continues to be a DELETE request,
            // which is not supported on the page you are redirecting to.
            prepareCategoriesModel(model);
            return "/categories";
        }
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        prepareProductsModel(model);
        return "/products";
    }

    @GetMapping("/products/add")
    public String getProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/productsAdd";
    }

    @PostMapping("/products/add")
    public String createProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                                @RequestParam("productImage") MultipartFile file,
                                @RequestParam("imgName") String imgName) {
        productService.createProduct(productDTO, file, imgName);

        return "redirect:/admin/products";
    }

    // FIXME: 15.03.2023 Why not @DeleteMapping ?
    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id", required = false) Product product,
                                Model model) {
        if (productService.deleteProduct(product)) {
            return "redirect:/admin/products";
        } else if (productService.productExists(product)) {
            model.addAttribute("deletingNonExistentProductError", "Deleting non-existent product");
            // FIXME: 15.03.2023 forward?
            // You cannot use forward.
            // When using forward, the request continues to be a DELETE request,
            // which is not supported on the page you are redirecting to.
            prepareProductsModel(model);
            return "/products";
        } else {
            model.addAttribute("deletingProductError", "Error deleting category");
            // FIXME: 15.03.2023 forward?
            // You cannot use forward.
            // When using forward, the request continues to be a DELETE request,
            // which is not supported on the page you are redirecting to.
            prepareProductsModel(model);
            return "/products";
        }
    }

    // FIXME: 15.03.2023 need to create update form (Currently, 1 form is used to create and update a product)
    @GetMapping("/products/update/{id}")
    public String updateProduct(@PathVariable(name = "id", required = false) Product product,
                                Model model) {
        // TODO: 15.03.2023 here
        if (productService.productExists(product)) {
            prepareCategoriesModel(model);
            model.addAttribute("productDTO", dtoConverter.convertToDto(product, ProductDTO.class));
            return "productsAdd";
        } else {
            model.addAttribute("updatingProductError", "Error updating product");
            // FIXME: 15.03.2023 forward?
            // You cannot use forward.
            // When using forward, the request continues to be a DELETE request,
            // which is not supported on the page you are redirecting to.
            prepareProductsModel(model);
            return "/products";
        }
    }

    private void prepareCategoriesModel(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
    }

    private void prepareProductsModel(Model model) {
        model.addAttribute("products", productService.getAllProducts());
    }
}
