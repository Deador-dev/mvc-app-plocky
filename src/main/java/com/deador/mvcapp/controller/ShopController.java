package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.Product;
import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.CategoryService;
import com.deador.mvcapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {
    private static final int DEFAULT_PAGE_SIZE = 16;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CartService cartService;

    @Autowired
    public ShopController(CategoryService categoryService,
                          ProductService productService,
                          CartService cartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/shop")
    public String viewShop(@AuthenticationPrincipal User user,
                           Model model) {
        return viewShopMain(user, 1, "id", "desc", model);
    }

    @GetMapping("/shop/page/{pageNo}")
    public String viewShopMain(@AuthenticationPrincipal User user,
                               @PathVariable(name = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        Page<Product> page = productService.findPaginated(pageNo, DEFAULT_PAGE_SIZE, sortField, sortDir);

        prepareModelForShop(user, pageNo, sortField, sortDir, model, page);
        model.addAttribute("pageUrlPrefix", "/shop");

        return "/shop";
    }

    @GetMapping("/shop/category/{id}/page/{pageNo}")
    public String viewShopByCategory(@AuthenticationPrincipal User user,
                                     @PathVariable(name = "id") Long id,
                                     @PathVariable(name = "pageNo") int pageNo,
                                     @RequestParam("sortField") String sortField,
                                     @RequestParam("sortDir") String sortDir,
                                     Model model) {
        Page<Product> page = productService.findPaginatedInCategory(pageNo, DEFAULT_PAGE_SIZE, sortField, sortDir, id);

        prepareModelForShop(user, pageNo, sortField, sortDir, model, page);
        model.addAttribute("pageUrlPrefix", "/shop/category/" + id);

        return "/shop";
    }

    @GetMapping("/search/page/{pageNo}")
    public String viewShopBySearch(@AuthenticationPrincipal User user,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @PathVariable(name = "pageNo") int pageNo,
                                   @RequestParam("sortField") String sortField,
                                   @RequestParam("sortDir") String sortDir,
                                   Model model) {
        Page<Product> page = productService.findPaginatedInSearch(pageNo, DEFAULT_PAGE_SIZE * 20, sortField, sortDir, keyword);

        prepareModelForShop(user, pageNo, sortField, sortDir, model, page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageUrlPrefix", "/search");

        return "/shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@AuthenticationPrincipal User user,
                              @PathVariable(name = "id") Long id,
                              Model model) {
        productService.incrementProductViewCountById(id);

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("cartCount", cartService.getCartQuantityByUser(user));

        return "/viewProduct";
    }

    private void prepareModelForShop(User user, int pageNo, String sortField, String sortDir, Model model, Page<Product> page) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("listProducts", page.getContent());
        model.addAttribute("cartCount", cartService.getCartQuantityByUser(user));

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
    }
}
