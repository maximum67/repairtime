package com.example.repairtime.controllers;

import com.example.repairtime.models.User;
import com.example.repairtime.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("title", "Авторизация");
        return "login";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("errorMessage", true);
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createNewUser(user)) {
            model.addAttribute("errorMessage", false);
            return "registration";
        }
        userService.createNewUser(user);
        return "redirect:/auth/login";
    }

}
