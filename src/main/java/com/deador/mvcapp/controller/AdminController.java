package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.repository.CategoryRepository;
import com.deador.mvcapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    public static String uploadPath = System.getProperty("user.dir") + "/src/main/resources/static/productImages/";
    private final CategoryService categoryService;

    @Autowired
    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
    public String getCategoryAdd(Model model){
        model.addAttribute("category", new Category());
        return "/categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String createCategory(@ModelAttribute("category") Category category){
        categoryService.createCategory(category);
        return "redirect:/admin/categories";
    }

}
