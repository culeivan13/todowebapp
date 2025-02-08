package com.suraj.todo.todowebapp.controller;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

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
