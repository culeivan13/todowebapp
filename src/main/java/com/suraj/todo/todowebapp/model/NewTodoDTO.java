package com.suraj.todo.todowebapp.model;

import java.time.LocalDateTime;

public class NewTodoDTO {
    private String title;
    private String description;
    private String priority;
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
