package com.suraj.todo.todowebapp.repository;

import com.suraj.todo.todowebapp.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<ToDo, Integer> {
}
