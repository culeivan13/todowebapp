package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.NewTodoDTO;
import com.suraj.todo.todowebapp.repository.TodoRepository;
import com.suraj.todo.todowebapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @ModelAttribute
    public void getUserTasks(Model model, HttpServletRequest request) {
        // Binding tasks to user home page which will always show it
        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");

        // Fetching user todos
        if (loggedInUser != null) {
            List<ToDo> tasks = loggedInUser.getTasks();
            model.addAttribute("tasks", tasks);
        }

        model.addAttribute("title", "User Home Page");
    }

    @GetMapping("/home")
    public String getUserHomePage(Model model) {
        model.addAttribute("showTodoForm", false);
        model.addAttribute("newtodo", new NewTodoDTO());
        return "userhome";
    }

    // Add a new todo
    @PostMapping("/process-todo")
    public String processNewTodo(@Valid @ModelAttribute("newtodo") NewTodoDTO newTodoDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 HttpServletRequest request) {

        System.out.println("New ToDo data --> " + newTodoDTO);

        // Handling form validation
        if (bindingResult.hasErrors()) {
            System.out.println("error -->" + bindingResult);
            model.addAttribute("showTodoForm", true);
            model.addAttribute("newtodo", newTodoDTO);
            return "userhome";
        }

        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");

        if (loggedInUser != null) {
            // Creating new todo object
            ToDo toDo = new ToDo();
            toDo.setTitle(newTodoDTO.getTitle());
            toDo.setDescription(newTodoDTO.getDescription());
            toDo.setPriority(newTodoDTO.getPriority());
            toDo.setDueDate(newTodoDTO.getDueDate());
            toDo.setCompleted(false);
            toDo.setUser(loggedInUser);

            // Saving the new todo into User and persisting in DB
            List<ToDo> tasks = loggedInUser.getTasks();
            tasks.add(toDo);
            loggedInUser.setTasks(tasks);
            User savedUser = userRepository.save(loggedInUser);
            System.out.println("Saved User --> " + savedUser);
        }

        return "redirect:/user/home";
    }
}
