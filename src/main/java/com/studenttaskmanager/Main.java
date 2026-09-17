package com.studenttaskmanager;

import com.studenttaskmanager.model.Category;
import com.studenttaskmanager.model.Priority;
import com.studenttaskmanager.model.Task;
import com.studenttaskmanager.service.TaskManager;
import com.studenttaskmanager.util.FileManager;
import com.studenttaskmanager.util.InputValidator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskManager taskManager = new TaskManager();
    private final FileManager fileManager = new FileManager();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        loadTasks();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInteger("Enter your choice: ");

            switch (choice) {
                case 1 -> addTask();
                case 2 -> displayTasks(taskManager.getAllTasks(), "All Tasks");
                case 3 -> searchTask();
                case 4 -> updateTask();
                case 5 -> deleteTask();
                case 6 -> markTaskCompleted();
                case 7 -> filterByCategory();
                case 8 -> filterByStatus();
                case 9 -> sortTasks();
                case 10 -> showDashboard();
                case 0 -> {
                    saveTasks();
                    running = false;
                    System.out.println("Application closed safely.");
                }
                default -> System.out.println("Invalid choice. Please select a menu option.");
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("===== Student Academic Task Manager =====");
        System.out.println("1. Add task");
        System.out.println("2. View all tasks");
        System.out.println("3. Search task");
        System.out.println("4. Update task");
        System.out.println("5. Delete task");
        System.out.println("6. Mark task as completed");
        System.out.println("7. Filter by category");
        System.out.println("8. Filter by completion status");
        System.out.println("9. Sort tasks");
        System.out.println("10. Show dashboard");
        System.out.println("0. Exit");
    }

    private void addTask() {
        System.out.println("\n--- Add Task ---");

        String title = readRequiredText("Enter title: ");
        String description = readRequiredText("Enter description: ");
        Category category = readCategory();
        LocalDate dueDate = readDate();
        Priority priority = readPriority();

        Task task = taskManager.addTask(title, description, category, dueDate, priority);
        saveTasks();

        System.out.println("Task added successfully. Task ID: " + task.getId());
    }

    private void searchTask() {
        String keyword = readRequiredText("Enter search keyword: ");
        displayTasks(taskManager.search(keyword), "Search Results");
    }

    private void updateTask() {
        System.out.println("\n--- Update Task ---");
        int id = readInteger("Enter task ID: ");
        Task task = taskManager.findById(id);

        if (task == null) {
            System.out.println("Task not found.");
            return;
        }

        String title = readRequiredText("Enter new title: ");
        String description = readRequiredText("Enter new description: ");
        Category category = readCategory();
        LocalDate dueDate = readDate();
        Priority priority = readPriority();

        taskManager.updateTask(id, title, description, category, dueDate, priority);
        saveTasks();

        System.out.println("Task updated successfully.");
    }

    private void deleteTask() {
        int id = readInteger("Enter task ID to delete: ");

        if (taskManager.deleteTask(id)) {
            saveTasks();
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private void markTaskCompleted() {
        int id = readInteger("Enter task ID to mark as completed: ");

        if (taskManager.markCompleted(id)) {
            saveTasks();
            System.out.println("Task marked as completed.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private void filterByCategory() {
        Category category = readCategory();
        displayTasks(taskManager.filterByCategory(category),
                "Tasks in " + category + " category");
    }

    private void filterByStatus() {
        System.out.println("\n1. Completed");
        System.out.println("2. Pending");

        int choice = readInteger("Choose status: ");

        if (choice == 1) {
            displayTasks(taskManager.filterByCompletion(true), "Completed Tasks");
        } else if (choice == 2) {
            displayTasks(taskManager.filterByCompletion(false), "Pending Tasks");
        } else {
            System.out.println("Invalid status choice.");
        }
    }

    private void sortTasks() {
        System.out.println("\n1. Sort by due date");
        System.out.println("2. Sort by priority");

        int choice = readInteger("Choose sorting method: ");

        if (choice == 1) {
            displayTasks(taskManager.sortByDueDate(), "Tasks Sorted by Due Date");
        } else if (choice == 2) {
            displayTasks(taskManager.sortByPriority(), "Tasks Sorted by Priority");
        } else {
            System.out.println("Invalid sorting choice.");
        }
    }

    private void showDashboard() {
        System.out.println("\n--- Task Dashboard ---");
        System.out.println("Total tasks: " + taskManager.getTotalCount());
        System.out.println("Completed tasks: " + taskManager.getCompletedCount());
        System.out.println("Pending tasks: " + taskManager.getPendingCount());

        System.out.println("\nTasks by category:");
        for (Category category : Category.values()) {
            System.out.println(category + ": " + taskManager.getCategoryCount(category));
        }
    }

    private void displayTasks(List<Task> tasks, String heading) {
        System.out.println("\n--- " + heading + " ---");

        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private Category readCategory() {
        while (true) {
            System.out.println("\nSelect category:");
            Category[] categories = Category.values();

            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i]);
            }

            int choice = readInteger("Choose category: ");
            Category category = InputValidator.parseCategory(choice);

            if (category != null) {
                return category;
            }

            System.out.println("Invalid category. Please try again.");
        }
    }

    private Priority readPriority() {
        while (true) {
            System.out.println("\nSelect priority:");
            Priority[] priorities = Priority.values();

            for (int i = 0; i < priorities.length; i++) {
                System.out.println((i + 1) + ". " + priorities[i]);
            }

            int choice = readInteger("Choose priority: ");
            Priority priority = InputValidator.parsePriority(choice);

            if (priority != null) {
                return priority;
            }

            System.out.println("Invalid priority. Please try again.");
        }
    }

    private LocalDate readDate() {
        while (true) {
            String value = readRequiredText("Enter due date (yyyy-MM-dd): ");
            LocalDate date = InputValidator.parseDate(value);

            if (date != null) {
                return date;
            }

            System.out.println("Invalid date. Use the format yyyy-MM-dd.");
        }
    }

    private String readRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (InputValidator.isValidTitle(value)) {
                return value;
            }

            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    private int readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private void loadTasks() {
        try {
            List<Task> savedTasks = fileManager.loadTasks();

            for (Task task : savedTasks) {
                taskManager.addExistingTask(task);
            }

            if (!savedTasks.isEmpty()) {
                System.out.println(savedTasks.size() + " saved task(s) loaded.");
            }
        } catch (IOException exception) {
            System.out.println("Could not load saved tasks: " + exception.getMessage());
        }
    }

    private void saveTasks() {
        try {
            fileManager.saveTasks(taskManager.getAllTasks());
        } catch (IOException exception) {
            System.out.println("Could not save tasks: " + exception.getMessage());
        }
    }
}
