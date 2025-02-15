package com.suraj.todo.todowebapp.repository;

import com.suraj.todo.todowebapp.entity.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<ToDo, Integer> {
    @Query(value = "select * from todo where todo.user_id = :userId", nativeQuery = true)
    List<ToDo> findTasksByUser(@Param("userId") int userId);

    @Query(value = "select * from todo where todo.user_id = :userId", nativeQuery = true)
    Page<ToDo> findTasksByUser(@Param("userId") int userId, Pageable pageable);
}
