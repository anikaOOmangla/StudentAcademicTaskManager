package com.studenttaskmanager.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
    private final int id;
    private String title;
    private String description;
    private Category category;
    private LocalDate dueDate;
    private Priority priority;
    private boolean completed;

    public Task(int id, String title, String description, Category category,
                LocalDate dueDate, Priority priority) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be positive.");
        }

        this.id = id;
        setTitle(title);
        setDescription(description);
        setCategory(category);
        setDueDate(dueDate);
        setPriority(priority);
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        this.title = title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            throw new IllegalArgumentException("Description cannot be null.");
        }
        this.description = description.trim();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
    if (category == null) {
        throw new IllegalArgumentException("Category cannot be null.");
    }
    this.category = category;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = Objects.requireNonNull(dueDate, "Due date cannot be null.");
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = Objects.requireNonNull(priority, "Priority cannot be null.");
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %d | %s | %s | Category: %s | Due: %s | Priority: %s | Status: %s",
                id, title, description, category, dueDate, priority,
                completed ? "Completed" : "Pending"
        );
    }
}
