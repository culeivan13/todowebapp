package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.NewTodoDTO;
import com.suraj.todo.todowebapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String getUserHomePage(Model model, HttpServletRequest request) {
        model.addAttribute("title", "User Home Page");

        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");

        // Fetching user todos
        if (loggedInUser != null) {
            List<ToDo> tasks = loggedInUser.getTasks();
            model.addAttribute("tasks", tasks);
        }

        model.addAttribute("unsuccess", false);
        model.addAttribute("newtodo", new NewTodoDTO());
        return "userhome";
    }

    @PostMapping("/process-todo")
    public String processNewTodo(@Valid @ModelAttribute("newtodo") NewTodoDTO newTodoDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 HttpServletRequest request) {

        System.out.println("New ToDo data --> " + newTodoDTO);

        // Handling form validation
        if (bindingResult.hasErrors()) {
            System.out.println("error -->" + bindingResult);
            model.addAttribute("unsuccess", true);
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
