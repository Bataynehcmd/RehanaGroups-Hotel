package com.RehanaGroups.web.controller;


import com.RehanaGroups.web.DTOs.SearchDTO;

import com.RehanaGroups.web.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class HomeController {

    private final RoomService roomService;

    @GetMapping("/home")
    public String getHomePage(Model model) {

        model.addAttribute("searchDTO", new SearchDTO(null, null, 0));

        model.addAttribute("rooms",roomService.findAll());

        return "home";
    }

}
