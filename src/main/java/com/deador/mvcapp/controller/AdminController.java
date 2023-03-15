package com.deador.mvcapp.controller;

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

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public AdminController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getAdminHome() {
        return "/adminHome";
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/categories";
    }

    @GetMapping("/categories/add")
    public String getCategoryAdd(Model model) {
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

    // FIXME: 14.03.2023 Why not @DeleteMapping ?
    @GetMapping("/categories/delete/{id}")
    public String getDeleteCategory(@PathVariable(name = "id", required = false) Category category,
                                    Model model) {
        if (categoryService.deleteCategory(category)) {
            return "redirect:/admin/categories";
        } else if (!categoryService.categoryExists(category)) {
            model.addAttribute("deletingNonExistentCategoryError", "Deleting non-existent category");
            return "forward:/admin/categories";
        } else {
            model.addAttribute("deletingCategoryError", "Error deleting category");
            return "forward:/admin/categories";
        }
    }

    // FIXME: 14.03.2023 Why not @PutMapping ?
    @GetMapping("/categories/update/{id}")
    public String getUpdateCategory(@PathVariable(name = "id", required = false) Category category,
                                    Model model) {
        if (categoryService.categoryExists(category)) {
            model.addAttribute("category", category);
            return "/categoriesAdd";
        } else {
            model.addAttribute("updatingCategoryError", "Error updating category");
            return "forward:/admin/categories";
        }
    }

    @GetMapping("/products")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/products";
    }

    @GetMapping("/products/add")
    public String getProductAdd(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "/productsAdd";
    }

    @PostMapping("/products/add")
    public String createProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                                @RequestParam("productImage") MultipartFile file) {
        productService.createProduct(productDTO, file);

        return "redirect:/admin/products";
    }

    // FIXME: 15.03.2023 Why not @DeleteMapping ?
    @GetMapping("/products/delete/{id}")
    public String getDeleteProduct(@PathVariable(name = "id", required = false) Product product,
                                   Model model) {
        if (productService.deleteProduct(product)) {
            return "redirect:/admin/products";
        } else if (productService.productExists(product)) {
            model.addAttribute("deletingNonExistentProductError", "Deleting non-existent product");
            return "forward:/admin/products";
        } else {
            model.addAttribute("deletingProductError", "Error deleting category");
            return "forward:/admin/products";
        }
    }
}
