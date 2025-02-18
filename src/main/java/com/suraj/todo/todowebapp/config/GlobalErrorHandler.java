package com.suraj.todo.todowebapp.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(Exception.class)
    public String handleGenericExceptions(Exception e, Model model) {
        model.addAttribute("errorMsg", e.getMessage());
        return "error";
    }
}
