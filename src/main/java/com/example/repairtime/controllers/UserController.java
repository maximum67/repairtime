package com.example.repairtime.controllers;

import com.example.repairtime.models.User;
import com.example.repairtime.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Arrays;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "Авторизация");
        return "login";
    }
    @GetMapping("/loginError")
    public String getLoginPageHasError(@RequestParam(name="error") String error, Model model) {
        model.addAttribute("title", "Ошибка авторизации");
        System.out.println(URLDecoder.decode(error, StandardCharsets.UTF_8));
        model.addAttribute("errorMessage", URLDecoder.decode(error, StandardCharsets.UTF_8));
        return "loginError";
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
