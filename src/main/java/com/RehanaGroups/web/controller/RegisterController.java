package com.RehanaGroups.web.controller;


import com.RehanaGroups.web.DTOs.UserDTO;
import com.RehanaGroups.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDTO userDTO) {
        userService.register(userDTO);
        return "redirect:/login";
    }

}
