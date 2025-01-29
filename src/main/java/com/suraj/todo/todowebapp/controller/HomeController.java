package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.SignUpDao;
import com.suraj.todo.todowebapp.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/home")
    public String getHomePage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        model.addAttribute("title", "Home Page");
        return "home";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("title", "Signup Page");
        model.addAttribute("signupdao", new SignUpDao());
        return "signup";
    }

    @PostMapping("/process")
    public String processSignup(@Valid @ModelAttribute("signupdao") SignUpDao signUpDao, BindingResult bindingResult, Model model) {
        System.out.println("signupdao --> " + signUpDao);

        if (bindingResult.hasErrors()) {
            System.out.println("Errors --> " + bindingResult.toString());
            model.addAttribute("signupdao", signUpDao);
            return "signup";
        } else {
            User user = new User();
            user.setName(signUpDao.getName());
            user.setEmail(signUpDao.getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(signUpDao.getPassword()));
            user.setRole("ROLE_USER");

            try {
                User savedUser = userRepository.save(user);
                System.out.println("Saved User --> " + savedUser);

                model.addAttribute("success", true);
                model.addAttribute("signupdao", new SignUpDao());
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                if (e.getClass() == DataIntegrityViolationException.class) {
                    model.addAttribute("error", true);
                    model.addAttribute("errorMsg", "This email is already taken!");
                    model.addAttribute("signupdao", signUpDao);
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
