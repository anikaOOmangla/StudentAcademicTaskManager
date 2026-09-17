package com.studenttaskmanager.util;

import com.studenttaskmanager.model.Category;
import com.studenttaskmanager.model.Priority;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class InputValidator {
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private InputValidator() {
    }

    public static boolean isValidTitle(String title) {
        return title != null && !title.isBlank();
    }

    public static boolean isValidDescription(String description) {
        return description != null && !description.isBlank();
    }

    public static LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value.trim(), DATE_FORMAT);
        } catch (DateTimeParseException | NullPointerException exception) {
            return null;
        }
    }

    public static Category parseCategory(int choice) {
        Category[] categories = Category.values();
        if (choice < 1 || choice > categories.length) {
            return null;
        }
        return categories[choice - 1];
    }

    public static Priority parsePriority(int choice) {
        Priority[] priorities = Priority.values();
        if (choice < 1 || choice > priorities.length) {
            return null;
        }
        return priorities[choice - 1];
    }
}
