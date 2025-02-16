package com.suraj.todo.todowebapp.service;

import com.suraj.todo.todowebapp.entity.ToDo;
import com.suraj.todo.todowebapp.entity.User;
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
}
