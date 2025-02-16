package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.EditTodoDTO;
import com.suraj.todo.todowebapp.model.NewTodoDTO;
import com.suraj.todo.todowebapp.repository.TodoRepository;
import com.suraj.todo.todowebapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    // Delete an existing todo
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteTodo(@PathVariable("id") int id) {
        try {
//            System.out.println("Deleting todo with id: " + id);
            todoRepository.deleteById(id);
            return new ResponseEntity<>("Task deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete task!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mark todo as done/undone
    @PostMapping("/done/{id}")
    public String changeTodoStatus(@PathVariable("id") int id) {
//        System.out.println("Changing todo status with id: " + id);

        // Getting the todo and toggling the isCompleted status
        ToDo todo = todoRepository.findById(id).get();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);

        return "redirect:/user/home";
    }

    // Open edit todo form
    @PostMapping("/edit/{id}")
    public String editTodo(@PathVariable("id") int id, Model model, HttpServletRequest request) {
//        System.out.println("Editing todo with id: " + id);
        model.addAttribute("title", "Edit todo Page");
        // Finding the todo
        ToDo todo = todoRepository.findById(id).get();

        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");

        // If todo doesn't belong to the logged in user then go back
        if (loggedInUser.getUserId() != todo.getUser().getUserId()) return "redirect:/user/home";

        // Creating newTodoDTO object
        EditTodoDTO editTodoDTO = new EditTodoDTO();

        // Binding the values from existing todo to newTodoDTO
        editTodoDTO.setTitle(todo.getTitle());
        editTodoDTO.setDescription(todo.getDescription());
        editTodoDTO.setPriority(todo.getPriority());
        editTodoDTO.setDueDate(todo.getDueDate());

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

        // Finding the todo
        ToDo todo = todoRepository.findById(id).get();

        // If todo doesn't belong to the logged in user then go back
        if (loggedInUser.getUserId() != todo.getUser().getUserId()) return "redirect:/user/home";

        // Setting changed valued to todo and saving them
        todo.setTitle(editTodoDTO.getTitle());
        todo.setDescription(editTodoDTO.getDescription());
        todo.setPriority(editTodoDTO.getPriority());
        todo.setDueDate(editTodoDTO.getDueDate());

        todoRepository.save(todo);

        return "redirect:/user/home";
    }

    // Open add todo form
    @GetMapping("/add")
    public String addTodo(Model model) {
        model.addAttribute("newtodo", new NewTodoDTO());
        return "add";
    }

    // Add a new todo
    @PostMapping("/process-todo")
    public String processNewTodo(@Valid @ModelAttribute("newtodo") NewTodoDTO newTodoDTO,
                                 BindingResult bindingResult,
                                 Model model,
                                 HttpServletRequest request) {

        model.addAttribute("title", "Add todo Page");
        System.out.println("New ToDo data --> " + newTodoDTO);

        // Fetching currently logged in user
        User loggedInUser = (User) request.getAttribute("loggedInUserObj");

        // Handling form validation
        if (bindingResult.hasErrors()) {
            System.out.println("error -->" + bindingResult);
            model.addAttribute("newtodo", newTodoDTO);
            return "add";
        }

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
