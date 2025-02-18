package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.EditTodoDTO;
import com.suraj.todo.todowebapp.model.NewTodoDTO;
import com.suraj.todo.todowebapp.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    // Delete an existing todo
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteTodo(@PathVariable("id") int id) {
        try {
//            System.out.println("Deleting todo with id: " + id);
            todoService.deleteTodoById(id);
            return new ResponseEntity<>("Task deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete task!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mark todo as done/undone
    @PostMapping("/done/{id}")
    public String changeTodoStatus(@PathVariable("id") int id) {
//        System.out.println("Changing todo status with id: " + id);
        todoService.changeTodoStatus(id);
        return "redirect:/user/home";
    }

    // Open edit todo form
    @PostMapping("/edit/{id}")
    public String editTodo(@PathVariable("id") int id, Model model, HttpServletRequest request) {
//        System.out.println("Editing todo with id: " + id);
        model.addAttribute("title", "Edit todo Page");

        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");
        EditTodoDTO editTodoDTO = todoService.getEditTodoDTO(id, loggedInUser);

        model.addAttribute("edittodo", editTodoDTO);
        model.addAttribute("todoId", id);

        return "edit";
    }

    // Process edit todo
    @PostMapping("/process-edit-todo/{id}")
    public String processNewTodo(@Valid @ModelAttribute("edittodo") EditTodoDTO editTodoDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 @PathVariable("id") int id,
                                 HttpServletRequest request) {

        System.out.println("Edited ToDo data --> " + editTodoDTO);

        // Handling form validation
        if (bindingResult.hasErrors()) {
            System.out.println("error -->" + bindingResult);
            model.addAttribute("showTodoForm", true);
            model.addAttribute("edittodo", editTodoDTO);
            model.addAttribute("todoId", id);
            return "edit";
        }

        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");

        todoService.editTodoById(editTodoDTO, id, loggedInUser);

        return "redirect:/user/home";
    }

    // Open add todo form
    @GetMapping("/add")
    public String addTodo(Model model) {
        model.addAttribute("title", "Add todo Page");
        model.addAttribute("newtodo", new NewTodoDTO());
        return "add";
    }

    // Add a new todo
    @PostMapping("/process-todo")
    public String processNewTodo(@Valid @ModelAttribute("newtodo") NewTodoDTO newTodoDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 HttpServletRequest request) {
        System.out.println("New ToDo data --> " + newTodoDTO);

        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");

        // Handling form validation
        if (bindingResult.hasErrors()) {
            System.out.println("error -->" + bindingResult);
            model.addAttribute("newtodo", newTodoDTO);
            return "add";
        }

        todoService.addNewTodo(newTodoDTO, loggedInUser);

        return "redirect:/user/home";
    }
}
