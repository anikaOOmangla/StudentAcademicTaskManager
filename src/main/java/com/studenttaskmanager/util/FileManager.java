package com.studenttaskmanager.util;

import com.studenttaskmanager.model.Category;
import com.studenttaskmanager.model.Priority;
import com.studenttaskmanager.model.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final Path DATA_FILE = Paths.get("data", "tasks.txt");

    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();

        ensureDataFileExists();

        try (BufferedReader reader = Files.newBufferedReader(DATA_FILE, StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                try {
                    Task task = parseTask(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (IllegalArgumentException exception) {
                    // Skip one malformed line so other saved tasks remain usable.
                }
            }
        }

        return tasks;
    }

    public void saveTasks(List<Task> tasks) throws IOException {
        ensureDataFileExists();

        try (BufferedWriter writer = Files.newBufferedWriter(DATA_FILE, StandardCharsets.UTF_8)) {
            for (Task task : tasks) {
                writer.write(formatTask(task));
                writer.newLine();
            }
        }
    }

    public Path getDataFile() {
        return DATA_FILE;
    }

    private void ensureDataFileExists() throws IOException {
        Path parent = DATA_FILE.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        if (Files.notExists(DATA_FILE)) {
            Files.createFile(DATA_FILE);
        }
    }

    private String formatTask(Task task) {
        return task.getId() + "\t"
                + cleanField(task.getTitle()) + "\t"
                + cleanField(task.getDescription()) + "\t"
                + task.getCategory().name() + "\t"
                + task.getDueDate() + "\t"
                + task.getPriority().name() + "\t"
                + task.isCompleted();
    }

    private Task parseTask(String line) {
        String[] fields = line.split("\t", -1);

        if (fields.length != 7) {
            throw new IllegalArgumentException("Invalid task record.");
        }

        int id = Integer.parseInt(fields[0]);
        Category category = Category.valueOf(fields[3]);
        LocalDate dueDate = LocalDate.parse(fields[4]);
        Priority priority = Priority.valueOf(fields[5]);
        boolean completed = Boolean.parseBoolean(fields[6]);

        Task task = new Task(id, fields[1], fields[2], category, dueDate, priority);
        task.setCompleted(completed);
        return task;
    }

    private String cleanField(String value) {
        return value.replace("\t", " ").replace("\r", " ").replace("\n", " ");
    }
}
