package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.entity.dto.OrderDTO;
import com.deador.mvcapp.factory.ObjectFactory;
import com.deador.mvcapp.service.CartItemService;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    private final ObjectFactory objectFactory;
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final OrderService orderService;

    @Autowired
    public CartController(ObjectFactory objectFactory,
                          CartService cartService,
                          CartItemService cartItemService,
                          OrderService orderService) {
        this.objectFactory = objectFactory;
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.orderService = orderService;
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

    @PostMapping("/buyNow/{id}")
    public String buyNow(@AuthenticationPrincipal User user,
                         @PathVariable(name = "id") Long id) {
        cartService.buyNowByProductId(user, id);

        return "redirect:/cart";
    }

    @DeleteMapping("/cart/delete/{id}")
    public String deleteCartItem(@AuthenticationPrincipal User user,
                                 @PathVariable(name = "id") Long id) {
        cartService.deleteProductFromCartByCartItemId(user, id);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String viewCheckout(@AuthenticationPrincipal User user,
                               Model model) {
        model.addAttribute("orderDTO", objectFactory.createObject(OrderDTO.class));
        model.addAttribute("total", cartService.getCartPriceByUser(user));
        model.addAttribute("cartCount", cartService.getCartQuantityByUser(user));

        return "/checkout";
    }

    @PostMapping("/checkout")
    public String createOrder(@AuthenticationPrincipal User user,
                              @ModelAttribute(name = "orderDTO") OrderDTO orderDTO) {
        orderService.createOrder(user, orderDTO);
        return "redirect:/shop";
    }

}
