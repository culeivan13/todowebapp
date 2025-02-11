package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.SignUpDTO;
import com.suraj.todo.todowebapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/")
    public String getHomePage(Model model) {
        model.addAttribute("title", "Home Page");
        return "home";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("title", "Signup Page");
        model.addAttribute("signupdto", new SignUpDTO());
        return "signup";
    }

    @PostMapping("/process")
    public String processSignup(@Valid @ModelAttribute("signupdto") SignUpDTO signUpDTO, BindingResult bindingResult, Model model) {
        System.out.println("signupdto --> " + signUpDTO);

        if (bindingResult.hasErrors()) {
            System.out.println("Errors --> " + bindingResult.toString());
            model.addAttribute("signupdto", signUpDTO);
            return "signup";
        } else {
            User user = new User();
            user.setName(signUpDTO.getName());
            user.setEmail(signUpDTO.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(signUpDTO.getPassword()));
            user.setRole("ROLE_USER");

            try {
                User savedUser = userRepository.save(user);
                System.out.println("Saved User --> " + savedUser);

                model.addAttribute("success", true);
                model.addAttribute("signupdto", new SignUpDTO());
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                if (e.getClass() == DataIntegrityViolationException.class) {
                    model.addAttribute("error", true);
                    model.addAttribute("errorMsg", "This email is already taken!");
                    model.addAttribute("signupdto", signUpDTO);
                }
            }
        }
        return "signup";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "Login here");
        return "login";
    }
}
