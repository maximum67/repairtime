package com.example.repairtime.controllers;

import com.example.repairtime.models.User;
import com.example.repairtime.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage( Model model) {
        model.addAttribute("title", "Авторизация");
        model.addAttribute("errorMessage", null);
        return "login";
    }
    @GetMapping("/login/error")
    public String getLoginPageHasError(@RequestParam(name="error") Exception exception,  Model model) {
        model.addAttribute("title", "Авторизация");
        model.addAttribute("errorMessage", exception.getMessage());
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

    @PostMapping("/logout")
    public String logout(Model model) {
        return "redirect:/auth/login";
    }

}
