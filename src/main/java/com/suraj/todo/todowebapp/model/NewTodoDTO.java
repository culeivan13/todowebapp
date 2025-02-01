package com.suraj.todo.todowebapp.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class NewTodoDTO {
    @NotBlank(message = "Title cannot be blank!")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;
    @NotBlank(message = "Description cannot be blank!")
    @Size(min = 20, max = 200, message = "Description must be between 20 and 200 characters")
    private String description;
    private String priority;
    @NotNull(message = "Date cannot be empty!")
    @Future(message = "Please select a future date and time!")
    private LocalDateTime dueDate;

    public NewTodoDTO() {
    }

    public NewTodoDTO(String title, String description, String priority, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "NewTodoDTO{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", dueDate=" + dueDate +
                '}';
    }
}
