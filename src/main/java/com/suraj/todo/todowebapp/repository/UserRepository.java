package com.suraj.todo.todowebapp.repository;

import com.suraj.todo.todowebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
