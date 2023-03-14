package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final CartService cartService;

    @Autowired
    public HomeController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/shop")
    public String getShop(@AuthenticationPrincipal User user,
                          Model model) {
        model.addAttribute("cartCount", cartService.findCartByUser(user).getQuantity());

        return "/shop";
    }
}
