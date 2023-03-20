package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.service.CartItemService;
import com.deador.mvcapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;

    @Autowired
    public CartController(CartService cartService,
                          CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/cart")
    public String viewCart(@AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("cartCount", cartService.getCartQuantityByUser(user));
        model.addAttribute("total", cartService.getCartPriceByUser(user));
        model.addAttribute("cart", cartItemService.getAllCartItemsByCartId(cartService.getCartByUser(user).getId()));

        return "/cart";
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@AuthenticationPrincipal User user,
                            @PathVariable(name = "id") Long id) {
        cartService.addProductToCartByProductId(user, id);
        return "redirect:/cart";
    }

    // TODO: 20.03.2023 need to create
    @PostMapping("/buyNow/{id}")
    public String buyNow(@AuthenticationPrincipal User user,
                         @PathVariable(name = "id") Long id) {
//        cartService.buyNowByProductId(user, id);
        return "";
    }

    @DeleteMapping("/cart/delete/{id}")
    public String deleteCartItem(@AuthenticationPrincipal User user,
                                 @PathVariable(name = "id") Long id) {
        cartService.deleteProductFromCartByCartItemId(user, id);
        return "redirect:/cart";
    }

}
