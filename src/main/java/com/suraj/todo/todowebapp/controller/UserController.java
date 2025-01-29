package com.suraj.todo.todowebapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/home")
    public String getUserHomePage(Model model) {
        model.addAttribute("title", "User Home Page");
        return "userhome";
    }
}
