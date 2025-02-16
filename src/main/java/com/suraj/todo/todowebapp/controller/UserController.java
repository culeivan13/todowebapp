package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.repository.TodoRepository;
import com.suraj.todo.todowebapp.repository.UserRepository;
import com.suraj.todo.todowebapp.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    private final int PAGE_SIZE = 10;

    @GetMapping("/home")
    public String getUserHomePage(Model model,
                                  HttpServletRequest request,
                                  @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
                                  @RequestParam(value = "sortBy", defaultValue = "due_date") String sortBy,
                                  @RequestParam(value = "direction", defaultValue = "desc") String direction
    ) {
        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");
        model.addAttribute("title", "User Home Page");

        // Defining the sorting order
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc") ?
                                            Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(sortDirection, sortBy);
        Sort sort = Sort.by(order);

        Page<ToDo> tasks = todoService.getUserTasks(loggedInUser, PAGE_SIZE, pageNumber, sort);
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", tasks.getTotalPages());
        return "userhome";
    }
}
