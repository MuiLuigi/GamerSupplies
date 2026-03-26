//Name: Fahad Arif (N01729165)
//Course: Web Application Development (CPAN-228)

package com.starmodestudios.gamersupplies.controller;

import com.starmodestudios.gamersupplies.model.User;
import com.starmodestudios.gamersupplies.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(user);
        } catch (IllegalArgumentException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register";
        }

        return "redirect:/login?registered=true";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}