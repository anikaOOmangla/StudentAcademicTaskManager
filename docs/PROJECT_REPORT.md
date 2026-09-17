# Student Academic Task Manager — Project Report Draft

> **Note:** This is a structured draft. Review and rewrite it according to the official format, terminology, screenshots, and submission requirements of your course.

## 1. Title

**Student Academic Task Manager**

## 2. Abstract

Student Academic Task Manager is an offline Java console application designed to help students organize academic tasks in one place. The application allows users to create and manage assignments, exams, lab work, projects, and other academic activities. Tasks can be searched, updated, deleted, filtered, sorted, marked as completed, and summarized through a simple dashboard. Task data is stored in a local text file so that it can be loaded again when the application starts.

## 3. Introduction

Students often have several academic activities with different deadlines and priorities. Keeping this information in separate notes can make it difficult to track pending work. This project demonstrates how a small Java application can provide a simple command-line solution for organizing academic tasks.

The application focuses on basic Java programming concepts rather than external services or complex frameworks. It uses classes, objects, encapsulation, collections, enums, file handling, exception handling, validation, and sorting.

## 4. Problem Statement

A student needs a simple way to record academic tasks and monitor their deadlines, categories, priorities, and completion status. The proposed application provides these functions through a menu-driven command-line interface and stores the information locally.

## 5. Objectives

- Create a simple task-management system for academic activities.
- Demonstrate object-oriented programming in Java.
- Store multiple tasks using `ArrayList`.
- Validate common user inputs.
- Provide searching, filtering, sorting, updating, and deletion.
- Save task information to a local file.
- Load saved information automatically.
- Handle common invalid inputs and file errors safely.
- Provide automated tests for important application logic.

## 6. Scope

The application covers personal academic task management through a local command-line interface.

### Included

- Assignment, exam, lab, project, and other categories
- Task title and description
- Due date
- Priority
- Completion status
- Search
- Update and deletion
- Filtering
- Sorting
- Summary information
- Local file persistence
- Input validation

### Not Included

- User accounts
- Online synchronization
- Cloud storage
- Notifications
- Database servers
- Mobile or web interfaces

## 7. Technologies Used

| Technology | Purpose |
|---|---|
| Java 17 | Application programming |
| Maven | Build and test automation |
| JUnit 5 | Automated unit testing |
| Java Collections | In-memory task storage |
| Java NIO / IO | Local file handling |

## 8. System Requirements

### Hardware

A normal computer capable of running a Java development environment is sufficient.

### Software

- JDK 17 or newer
- Maven 3.8 or newer
- Terminal or command prompt

No graphical IDE is required.

## 9. System Design

The application uses a simple layered organization:

```text
User
  |
  v
Main
  |
  +----> TaskManager
  |         |
  |         +----> Task
  |         +----> Category
  |         +----> Priority
  |
  +----> InputValidator
  |
  +----> FileManager
             |
             v
        data/tasks.txt
```

### Design Explanation

- `Main` manages user interaction and the menu.
- `TaskManager` manages the collection of tasks and application logic.
- `Task` represents one task.
- `Category` and `Priority` represent fixed choices using enums.
- `InputValidator` performs reusable validation and parsing.
- `FileManager` handles reading and writing task records.

## 10. Module Description

### Task Module

The `Task` class stores task information and provides getters and setters for editable fields.

### Task Management Module

`TaskManager` performs operations such as:

- Add
- Find
- Search
- Update
- Delete
- Complete
- Filter
- Sort
- Count

### Input Validation Module

`InputValidator` checks required text, dates, category choices, and priority choices.

### File Management Module

`FileManager` creates the data directory when necessary and saves/loads task records from a text file.

### User Interface Module

`Main` provides the menu-driven command-line interface.

## 11. Class Design

### `Task`

Important attributes:

- `id`
- `title`
- `description`
- `category`
- `dueDate`
- `priority`
- `completed`

### `Category`

Possible values:

- `ASSIGNMENT`
- `EXAM`
- `LAB`
- `PROJECT`
- `OTHER`

### `Priority`

Possible values:

- `LOW`
- `MEDIUM`
- `HIGH`

### `TaskManager`

Maintains an `ArrayList` of tasks and provides task operations.

### `FileManager`

Responsible for persistent local storage.

### `InputValidator`

Responsible for common input conversion and validation.

### `Main`

Controls the application flow and connects user input with the other classes.

## 12. Application Workflow

```text
Start
  |
  v
Create/verify data file
  |
  v
Load saved tasks
  |
  v
Display menu
  |
  +--> Add task --------+
  |                     |
  +--> View tasks       |
  |                     |
  +--> Search           |
  |                     |
  +--> Update           |
  |                     |
  +--> Delete           |
  |                     |
  +--> Complete         |
  |                     |
  +--> Filter           |
  |                     |
  +--> Sort             |
  |                     |
  +--> Dashboard        |
  |                     |
  +--> Exit --> Save ---+
             |
             v
            End
```

## 13. Algorithms / Logic Used

### Adding a Task

1. Read title and description.
2. Read category.
3. Read due date.
4. Read priority.
5. Create a `Task` object with the next available ID.
6. Add the object to the `ArrayList`.
7. Save the current list to the file.

### Searching

The entered keyword is converted to lowercase. Each task's ID, title, description, and category are checked for the keyword. Matching tasks are returned.

### Filtering

For category filtering, each task's category is compared with the selected category.

For completion filtering, each task's `completed` value is compared with the requested status.

### Sorting by Due Date

The list is copied and sorted using a comparator based on `LocalDate`. The original list order is not changed.

### Sorting by Priority

The list is copied and sorted using the `Priority` enum order in reverse, so `HIGH` appears before `MEDIUM`, followed by `LOW`. Due date and ID are used as additional ordering fields.

### Dashboard

The application counts total, completed, pending, and category-specific tasks.

## 14. Exception Handling

The application handles exceptions in several places.

- `NumberFormatException` handles non-numeric menu and ID input.
- Date parsing errors are handled by the date validator.
- `IllegalArgumentException` protects the task model from invalid values.
- `IOException` handles file reading and writing problems.
- Invalid records in the saved file are skipped rather than stopping the entire load process.

## 15. File Handling

Task data is stored at:

```text
data/tasks.txt
```

The application uses Java NIO classes such as `Files`, `Path`, `BufferedReader`, and `BufferedWriter`.

The data file uses tab-separated fields:

```text
id    title    description    category    dueDate    priority    completed
```

The application creates the directory and file automatically when needed.

## 16. Testing

JUnit 5 tests are included in:

```text
src/test/java/com/studenttaskmanager/service/TaskManagerTest.java
```

The tests cover core task-management behavior without requiring user interaction.

Run all tests with:

```bash
mvn test
```

## 17. Sample Test Cases

| Test Case | Input/Action | Expected Result |
|---|---|---|
| Add task | Add a valid task | Task is stored with a new ID |
| Delete task | Delete an existing ID | Task is removed |
| Delete invalid task | Delete ID 99 when absent | Operation returns false |
| Search | Search for `math` | Matching task is returned |
| Complete | Mark an existing ID complete | Status becomes completed |
| Invalid title | Add task with empty title | `IllegalArgumentException` |
| Filter category | Select LAB | Only LAB tasks are returned |
| Filter status | Select completed | Only completed tasks are returned |
| Sort date | Sort by due date | Earliest due date appears first |
| Sort priority | Sort by priority | HIGH appears before MEDIUM and LOW |
| Update | Change an existing task | Updated fields are stored |
| Dashboard | Count tasks | Correct totals are displayed |

## 18. Results

The completed application provides the planned offline academic task-management operations through a command-line menu. Tasks can be stored in memory while the application runs and persisted to a local text file for later use.

Before final submission, add actual screenshots or terminal output from your own test run if the course report requires evidence of execution.

## 19. Limitations

- Data is stored in a simple text file rather than a database.
- The application is intended for one local user.
- There are no reminders or notifications.
- There is no graphical user interface.
- There is no cloud synchronization.
- The text-file format is simple and intended for this application.

## 20. Future Enhancements

Possible future improvements include:

- A graphical interface
- Database storage
- Task reminders
- Calendar view
- More detailed deadline analysis
- Exporting task summaries
- Recurring academic tasks

These enhancements are outside the current project scope.

## 21. Conclusion

Student Academic Task Manager demonstrates how core Java concepts can be combined to solve a practical academic organization problem. The project uses object-oriented classes, enums, collections, validation, exception handling, sorting, and file handling in a small and understandable application. The command-line design keeps the project simple to execute and study while still providing a complete set of task-management operations.

## 22. References

The implementation primarily uses the Java standard library and JUnit 5. No research paper or external factual source is required for the implementation.

For final submission, use the official documentation or course-provided references required by your instructor. Do not add references that you did not actually consult.
