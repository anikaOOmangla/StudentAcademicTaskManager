package com.studenttaskmanager.service;

import com.studenttaskmanager.model.Category;
import com.studenttaskmanager.model.Priority;
import com.studenttaskmanager.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class TaskManager {
    private final List<Task> tasks = new ArrayList<>();
    private int nextId = 1;

    public Task addTask(String title, String description, Category category,
                        LocalDate dueDate, Priority priority) {
        Task task = new Task(nextId, title, description, category, dueDate, priority);
        tasks.add(task);
        nextId++;
        return task;
    }

    public boolean addExistingTask(Task task) {
        if (task == null || findById(task.getId()) != null) {
            return false;
        }

        tasks.add(task);
        nextId = Math.max(nextId, task.getId() + 1);
        return true;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public Task findById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public List<Task> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return new ArrayList<>();
        }

        String searchText = keyword.trim().toLowerCase(Locale.ROOT);
        List<Task> results = new ArrayList<>();

        for (Task task : tasks) {
            if (String.valueOf(task.getId()).contains(searchText)
                    || task.getTitle().toLowerCase(Locale.ROOT).contains(searchText)
                    || task.getDescription().toLowerCase(Locale.ROOT).contains(searchText)
                    || task.getCategory().name().toLowerCase(Locale.ROOT).contains(searchText)) {
                results.add(task);
            }
        }

        return results;
    }

    public boolean updateTask(int id, String title, String description,
                              Category category, LocalDate dueDate, Priority priority) {
        Task task = findById(id);
        if (task == null) {
            return false;
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setCategory(category);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        return true;
    }

    public boolean deleteTask(int id) {
        Task task = findById(id);
        return task != null && tasks.remove(task);
    }

    public boolean markCompleted(int id) {
        Task task = findById(id);
        if (task == null) {
            return false;
        }

        task.markCompleted();
        return true;
    }

    public List<Task> filterByCategory(Category category) {
        List<Task> results = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getCategory() == category) {
                results.add(task);
            }
        }

        return results;
    }

    public List<Task> filterByCompletion(boolean completed) {
        List<Task> results = new ArrayList<>();

        for (Task task : tasks) {
            if (task.isCompleted() == completed) {
                results.add(task);
            }
        }

        return results;
    }

    public List<Task> sortByDueDate() {
        List<Task> sorted = getAllTasks();
        sorted.sort(Comparator.comparing(Task::getDueDate)
                .thenComparingInt(Task::getId));
        return sorted;
    }

    public List<Task> sortByPriority() {
        List<Task> sorted = getAllTasks();
        sorted.sort(Comparator.comparing(Task::getPriority, Comparator.reverseOrder())
                .thenComparing(Task::getDueDate)
                .thenComparingInt(Task::getId));
        return sorted;
    }

    public int getTotalCount() {
        return tasks.size();
    }

    public int getCompletedCount() {
        int count = 0;
        for (Task task : tasks) {
            if (task.isCompleted()) {
                count++;
            }
        }
        return count;
    }

    public int getPendingCount() {
        return getTotalCount() - getCompletedCount();
    }

    public int getCategoryCount(Category category) {
        int count = 0;
        for (Task task : tasks) {
            if (task.getCategory() == category) {
                count++;
            }
        }
        return count;
    }

    public void clear() {
        tasks.clear();
        nextId = 1;
    }
}
