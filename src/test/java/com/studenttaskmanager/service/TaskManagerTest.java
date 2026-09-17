package com.studenttaskmanager.service;

import com.studenttaskmanager.model.Category;
import com.studenttaskmanager.model.Priority;
import com.studenttaskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();

        taskManager.addTask(
                "Math Assignment",
                "Complete integration questions",
                Category.ASSIGNMENT,
                LocalDate.of(2026, 9, 25),
                Priority.HIGH
        );

        taskManager.addTask(
                "Java Lab",
                "Practice file handling",
                Category.LAB,
                LocalDate.of(2026, 9, 20),
                Priority.MEDIUM
        );

        taskManager.addTask(
                "AI Project",
                "Prepare project report",
                Category.PROJECT,
                LocalDate.of(2026, 10, 1),
                Priority.LOW
        );
    }

    @Test
    void shouldAddTaskWithNextId() {
        Task task = taskManager.addTask(
                "DBMS Exam",
                "Revise normalization",
                Category.EXAM,
                LocalDate.of(2026, 10, 5),
                Priority.HIGH
        );

        assertEquals(4, task.getId());
        assertEquals(4, taskManager.getTotalCount());
    }

    @Test
    void shouldDeleteExistingTask() {
        assertTrue(taskManager.deleteTask(2));
        assertNull(taskManager.findById(2));
        assertEquals(2, taskManager.getTotalCount());
    }

    @Test
    void shouldReturnFalseWhenDeletingMissingTask() {
        assertFalse(taskManager.deleteTask(99));
        assertEquals(3, taskManager.getTotalCount());
    }

    @Test
    void shouldSearchByTitleAndDescription() {
        List<Task> titleResults = taskManager.search("math");
        List<Task> descriptionResults = taskManager.search("file handling");

        assertEquals(1, titleResults.size());
        assertEquals("Math Assignment", titleResults.get(0).getTitle());

        assertEquals(1, descriptionResults.size());
        assertEquals("Java Lab", descriptionResults.get(0).getTitle());
    }

    @Test
    void shouldMarkTaskCompleted() {
        assertTrue(taskManager.markCompleted(1));
        assertTrue(taskManager.findById(1).isCompleted());
        assertEquals(1, taskManager.getCompletedCount());
        assertEquals(2, taskManager.getPendingCount());
    }

    @Test
    void shouldRejectInvalidTaskData() {
        assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.addTask(
                        "",
                        "Some description",
                        Category.ASSIGNMENT,
                        LocalDate.of(2026, 9, 25),
                        Priority.LOW
                )
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.addTask(
                        "Valid title",
                        "Some description",
                        null,
                        LocalDate.of(2026, 9, 25),
                        Priority.LOW
                )
        );
    }

    @Test
    void shouldFilterByCategory() {
        List<Task> results = taskManager.filterByCategory(Category.LAB);

        assertEquals(1, results.size());
        assertEquals("Java Lab", results.get(0).getTitle());
    }

    @Test
    void shouldFilterByCompletionStatus() {
        taskManager.markCompleted(1);

        assertEquals(1, taskManager.filterByCompletion(true).size());
        assertEquals(2, taskManager.filterByCompletion(false).size());
    }

    @Test
    void shouldSortByDueDate() {
        List<Task> sorted = taskManager.sortByDueDate();

        assertEquals("Java Lab", sorted.get(0).getTitle());
        assertEquals("Math Assignment", sorted.get(1).getTitle());
        assertEquals("AI Project", sorted.get(2).getTitle());
    }

    @Test
    void shouldSortByPriorityWithHighFirst() {
        List<Task> sorted = taskManager.sortByPriority();

        assertEquals(Priority.HIGH, sorted.get(0).getPriority());
        assertEquals(Priority.MEDIUM, sorted.get(1).getPriority());
        assertEquals(Priority.LOW, sorted.get(2).getPriority());
    }

    @Test
    void shouldUpdateTask() {
        boolean updated = taskManager.updateTask(
                1,
                "Updated Assignment",
                "Updated description",
                Category.PROJECT,
                LocalDate.of(2026, 11, 1),
                Priority.MEDIUM
        );

        assertTrue(updated);

        Task task = taskManager.findById(1);
        assertNotNull(task);
        assertEquals("Updated Assignment", task.getTitle());
        assertEquals(Category.PROJECT, task.getCategory());
        assertEquals(LocalDate.of(2026, 11, 1), task.getDueDate());
        assertEquals(Priority.MEDIUM, task.getPriority());
    }

    @Test
    void shouldCountCategories() {
        assertEquals(1, taskManager.getCategoryCount(Category.ASSIGNMENT));
        assertEquals(1, taskManager.getCategoryCount(Category.LAB));
        assertEquals(1, taskManager.getCategoryCount(Category.PROJECT));
        assertEquals(0, taskManager.getCategoryCount(Category.EXAM));
    }
}
