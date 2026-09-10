package com.RehanaGroups.web.controller;

import com.RehanaGroups.web.DTOs.EditDTO;
import com.RehanaGroups.web.entity.User;
import com.RehanaGroups.web.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/profile")
    public String userProfile(Authentication auth, Model model) {
        String username = auth.getName();
        User user = userService.findByUserName(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editUserProfilePage(Authentication auth, Model model) {
        String username = auth.getName();
        User user = userService.findByUserName(username);
        EditDTO editDTO = new EditDTO(user.getFirstName(), user.getLastName(), user.getEmail());
        model.addAttribute("editDTO", editDTO);
        return "profile/edit";
    }

    @PostMapping("/profile/edit")
    public String editUserProfile(Authentication auth, @Valid @ModelAttribute EditDTO editDTO) {
        String username = auth.getName();
        User user = userService.findByUserName(username);
        userService.updateProfile(user, editDTO);
        return "redirect:/profile";
    }


}
