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
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Optional;

@Controller
public class LoginController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public LoginController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, RoleRepository roleRepository, CustomUserDetailService customUserDetailService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.customUserDetailService = customUserDetailService;
    }

    @GetMapping("/login")
    private String getLogin() {
        return "/login";
    }

    @GetMapping("/register")
    private String getRegister() {
        return "/register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute("user") User user,
                             HttpServletRequest request,
                             Model model) throws ServletException {
        Optional<User> userFromDB = userService.getUserByEmail(user.getEmail());

        if (userFromDB.isPresent() && user.getEmail().equals(userFromDB.get().getEmail())) {
            model.addAttribute("authenticationError", "Authentication error");
            return "/register";
        }

        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        user.setRoles(Collections.singletonList(roleRepository.findById(2L).get()));

        userService.createUser(user);
        request.login(user.getEmail(), password);

        return "redirect:/";
    }
}
