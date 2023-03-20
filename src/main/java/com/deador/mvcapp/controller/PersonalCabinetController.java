package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.service.CartService;
import com.deador.mvcapp.service.OrderItemService;
import com.deador.mvcapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalCabinetController {
    private final CartService cartService;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Autowired
    public PersonalCabinetController(CartService cartService,
                                     OrderService orderService,
                                     OrderItemService orderItemService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/personalCabinet")
    public String getPersonalCabinet(@AuthenticationPrincipal User user,
                                     Model model) {
        model.addAttribute("listOrders", orderService.getAllOrdersReverseByUserId(user.getId()));
        model.addAttribute("listOrderItems", orderItemService.getAllOrderItemsReverseByUser(user));
        model.addAttribute("user", user);
        model.addAttribute("cartCount", cartService.getCartQuantityByUser(user));

        return "/personalCabinet";
    }
}
