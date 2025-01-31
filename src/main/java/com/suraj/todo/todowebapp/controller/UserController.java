package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.model.NewTodoDTO;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private List<ToDo> tasks;

    @PostConstruct
    private void populateSomeTasks() {
        tasks = new ArrayList<>();
        System.out.println("Populating some tasks!!!");
        tasks.add(new ToDo(1, "Buy groceries", "Purchase vegetables, fruits, and dairy products", false, "High", LocalDateTime.of(2025, 2, 1, 18, 0)));
        tasks.add(new ToDo(2, "Complete project report", "Finish the final draft and submit it", false, "High", LocalDateTime.of(2025, 2, 5, 23, 59)));
        tasks.add(new ToDo(3, "Doctor appointment", "Visit Dr. Smith for a routine check-up", false, "Medium", LocalDateTime.of(2025, 2, 3, 10, 30)));
        tasks.add(new ToDo(4, "Car service", "Take the car for scheduled maintenance", false, "Low", LocalDateTime.of(2025, 2, 10, 14, 0)));
        tasks.add(new ToDo(5, "Book flight tickets", "Book tickets for the vacation", true, "High", LocalDateTime.of(2025, 1, 29, 20, 0)));
        tasks.add(new ToDo(6, "Renew gym membership", "Extend gym subscription for another 6 months", false, "Medium", LocalDateTime.of(2025, 2, 15, 9, 0)));
        tasks.add(new ToDo(7, "Team meeting", "Attend the weekly team sync-up", false, "High", LocalDateTime.of(2025, 2, 2, 16, 0)));
        tasks.add(new ToDo(8, "Fix leaking faucet", "Call a plumber to repair the kitchen faucet", false, "Medium", LocalDateTime.of(2025, 2, 6, 11, 0)));
        tasks.add(new ToDo(9, "Read a book", "Finish reading 'Atomic Habits'", true, "Low", LocalDateTime.of(2025, 1, 25, 22, 0)));
        tasks.add(new ToDo(10, "Pay electricity bill", "Clear the monthly electricity bill online", false, "High", LocalDateTime.of(2025, 2, 4, 23, 59)));
    }

    @GetMapping("/home")
    public String getUserHomePage(Model model) {
        model.addAttribute("title", "User Home Page");
        model.addAttribute("tasks", tasks);
        model.addAttribute("newtodo", new NewTodoDTO());
        return "userhome";
    }

    @PostMapping("/process-todo")
    public String processNewTodo(@ModelAttribute("newtodo") NewTodoDTO newTodoDTO) {
        System.out.println(newTodoDTO);
        return "userhome";
    }
}
