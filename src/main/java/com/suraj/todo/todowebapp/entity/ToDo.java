package com.suraj.todo.todowebapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "todo")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todoId;
    private String title;
    private boolean isCompleted;
    private String priority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
