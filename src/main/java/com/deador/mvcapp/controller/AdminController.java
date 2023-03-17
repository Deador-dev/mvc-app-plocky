package com.deador.mvcapp.controller;

import com.deador.mvcapp.converter.DTOConverter;
import com.deador.mvcapp.entity.Category;
import com.deador.mvcapp.entity.dto.ProductDTO;
import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.service.CategoryService;
import com.deador.mvcapp.service.OrderService;
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
    private final OrderService orderService;
    private final DTOConverter dtoConverter;
    private final ObjectFactory objectFactory;

    @Autowired
    public AdminController(CategoryService categoryService,
                           ProductService productService,
                           OrderService orderService,
                           DTOConverter dtoConverter,
                           ObjectFactory objectFactory) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.orderService = orderService;
        this.dtoConverter = dtoConverter;
        this.objectFactory = objectFactory;
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
        // FIXME: 17.03.2023 !new Category()
        model.addAttribute("category", objectFactory.createObject(Category.class));
        return "/categoriesAdd";
    }

    @PostMapping("/categories/add")
    public String createCategory(@ModelAttribute("category") Category category,
                                 Model model) {
        categoryService.createOrUpdateCategory(category);
        prepareCategoriesModel(model);
        return "redirect:/admin/categories";

    }

    @DeleteMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Long id,
                                 Model model) {
        categoryService.deleteCategoryById(id);
        prepareCategoriesModel(model);
        return "redirect:/admin/categories";
    }

    // FIXME: 15.03.2023 need to create update form (Currently, 1 form is used to create and update a category)
    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable(name = "id") Long id,
                                 Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "/categoriesAdd";
    }

    @GetMapping("/products")
    public String viewProducts(Model model) {
        prepareProductsModel(model);
        return "/products";
    }

    @GetMapping("/products/add")
    public String getProductForm(Model model) {
        // FIXME: 17.03.2023 !new ProductDTO()
        model.addAttribute("productDTO", objectFactory.createObject(ProductDTO.class));
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

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id,
                                Model model) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

    // FIXME: 15.03.2023 need to create update form (Currently, 1 form is used to create and update a product)
    @GetMapping("/products/update/{id}")
    public String updateProduct(@PathVariable(name = "id") Long id,
                                Model model) {
        prepareCategoriesModel(model);
        model.addAttribute("productDTO", dtoConverter.convertToDto(productService.getProductById(id), ProductDTO.class));
        return "productsAdd";
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        prepareOrdersModel(model);
        return "/orders";
    }

    @GetMapping("/order/orderDetails/{id}")
    public String viewOrderDetails(@PathVariable(name = "id") Long id,
                                   Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        // TODO: 17.03.2023 model.addAttribute("listOrderItems", orderItemService.getAllOrderItemsById(id))

        return "/orderDetails";
    }


    private void prepareCategoriesModel(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
    }

    private void prepareProductsModel(Model model) {
        model.addAttribute("products", productService.getAllProducts());
    }

    private void prepareOrdersModel(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
    }
}
