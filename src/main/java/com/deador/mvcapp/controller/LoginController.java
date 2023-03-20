package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.User;
import com.deador.mvcapp.repository.RoleRepository;
import com.deador.mvcapp.service.UserService;
import com.deador.mvcapp.service.userdetail.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
public class LoginController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public LoginController(UserService userService,
                           RoleRepository roleRepository,
                           CustomUserDetailService customUserDetailService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.customUserDetailService = customUserDetailService;
    }

    @GetMapping("/login")
    private String getLogin() {
        // TODO: 20.03.2023 need to create functionality to check user.active = true/false for login
        return "/login";
    }

    @GetMapping("/register")
    private String getRegister() {
        return "/register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") User user,
                             HttpServletRequest request) {
        userService.createUserAndRequestLogin(user, request);

        return "redirect:/shop";
    }

    @GetMapping("/activate/{code}")
    public String activateAccount(@PathVariable(name = "code") String code,
                                  Model model) {
        if (userService.activateUser(code)) {
            // FIXME: 20.03.2023 don't work model
            model.addAttribute("activationMessage", "User successfully activated!");
        }
        return "/login";
    }
}
