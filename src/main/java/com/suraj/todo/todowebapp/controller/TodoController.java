package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") int id) {
        System.out.println("Deleting todo with id: " + id);
        todoRepository.deleteById(id);
        return "redirect:/user/home";
    }

    @PostMapping("/done/{id}")
    public String changeTodoStatus(@PathVariable("id") int id) {
//        System.out.println("Changing todo status with id: " + id);

        // Getting the todo and toggling the isCompleted status
        ToDo todo = todoRepository.findById(id).get();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);

        return "redirect:/user/home";
    }
}
