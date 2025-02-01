package com.suraj.todo.todowebapp.config;

import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttribute {

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void setIsLoggedIn(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isLoggedIn = authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());

        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email);
        if(user != null) {
            System.out.println("Logged in user: " + user.getName());
            model.addAttribute("loggedInUser", user.getName());
        }

        model.addAttribute("isLoggedIn", isLoggedIn);
        request.setAttribute("loggedInUserObj", user);
    }
}
