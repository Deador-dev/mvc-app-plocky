package com.deador.mvcapp.controller;

import com.deador.mvcapp.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonalCabinetController {
    @GetMapping("/personalCabinet")
    public String getPersonalCabinet(@AuthenticationPrincipal User user,
                                     Model model) {


        return "personalCabinet";
    }
}
