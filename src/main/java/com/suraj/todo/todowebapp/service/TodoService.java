package com.suraj.todo.todowebapp.service;

import com.suraj.todo.todowebapp.config.ForbiddenUserException;
import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.entity.User;
import com.suraj.todo.todowebapp.model.EditTodoDTO;
import com.suraj.todo.todowebapp.model.NewTodoDTO;
import com.suraj.todo.todowebapp.repository.TodoRepository;
import com.suraj.todo.todowebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TodoRepository todoRepository;

    public List<ToDo> getUserTasks(User loggedInUser) {
        // Fetching user todos
        List<ToDo> tasks = todoRepository.findTasksByUser(loggedInUser.getUserId());

        return tasks;
    }

    public Page<ToDo> getUserTasks(User loggedInUser, int pageSize, int pageNumber, Sort sort) {
        //Making pageable based in input
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        // Fetching user todos
        Page<ToDo> tasks = todoRepository.findTasksByUser(loggedInUser.getUserId(), pageable);

        return tasks;
    }

    public void deleteTodoById(int id) throws Exception {
        todoRepository.deleteById(id);
    }

    public void changeTodoStatus(int id) {
        // Getting the todo and toggling the isCompleted status
        ToDo todo = todoRepository.findById(id).get();
        todo.setCompleted(!todo.isCompleted());
        todoRepository.save(todo);
    }

    public EditTodoDTO getEditTodoDTO(int id, User loggedInUser) {
        // Finding the todo
        ToDo todo = todoRepository.findById(id).get();

        // If todo doesn't belong to the logged in user then return throw error
        if (loggedInUser.getUserId() != todo.getUser().getUserId())
            throw new ForbiddenUserException("You are not authorised!");

        // Creating newTodoDTO object
        EditTodoDTO editTodoDTO = new EditTodoDTO();

        // Binding the values from existing todo to newTodoDTO
        editTodoDTO.setTitle(todo.getTitle());
        editTodoDTO.setDescription(todo.getDescription());
        editTodoDTO.setPriority(todo.getPriority());
        editTodoDTO.setDueDate(todo.getDueDate());

        return editTodoDTO;
    }

    public ToDo editTodoById(EditTodoDTO editTodoDTO, int id, User loggedInUser) {
        // Finding the todo
        ToDo todo = todoRepository.findById(id).get();

        // If todo doesn't belong to the logged in user then throw error
        if (loggedInUser.getUserId() != todo.getUser().getUserId())
            throw new ForbiddenUserException("You are not authorised!");

        // Setting changed valued to todo and saving them
        todo.setTitle(editTodoDTO.getTitle());
        todo.setDescription(editTodoDTO.getDescription());
        todo.setPriority(editTodoDTO.getPriority());
        todo.setDueDate(editTodoDTO.getDueDate());

        ToDo savedTodo = todoRepository.save(todo);

        return savedTodo;
    }

    public void addNewTodo(NewTodoDTO newTodoDTO, User loggedInUser) {
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
    }
}
